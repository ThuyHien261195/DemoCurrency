package com.example.democurrency.presentation

import app.cash.turbine.test
import com.example.core.kttestutils.MainDispatcherRule
import com.example.democurrency.presentation.ui.StateEvent
import com.example.mediator.currencyinfo.domain.CurrencyRepository
import com.example.mediator.currencyinfo.domain.model.CurrencyGroupModel
import com.example.mediator.currencyinfo.presentation.mapper.toCurrencyInfoModel
import com.example.mediator.currencyinfo.presentation.uimodel.CurrencyGroupUiModel
import com.example.mediator.currencyinfo.presentation.uimodel.CurrencyInfoUiModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: CurrencyRepository
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `test groupsFlow emits correct groups`() = runTest {
        coEvery { repository.getAllGroupInfos() } returns flowOf(testCurrencyGroupModelList)

        viewModel = MainViewModel(repository)

        viewModel.groupsFlow.test {
            val actual1 = awaitItem()
            assertEquals(persistentListOf<CurrencyGroupUiModel>(), actual1)

            val actual2 = awaitItem()
            assertEquals(2, actual2.size)
            assertEquals(testCurrencyGroupUiModelList, actual2)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test initDb emits state events successfully`() = runTest {
        coEvery { repository.initData() } returns Unit

        viewModel = MainViewModel(repository)

        viewModel.stateEventFlow.test {
            assertEquals(StateEvent.Loading, awaitItem())
            assertEquals(StateEvent.InitSuccess, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        coVerify { repository.initData() }
    }

    @Test
    fun `test clearDb emits state events successfully`() = runTest {
        coEvery { repository.clearAllDb() } returns Unit

        viewModel.clearDb()

        viewModel.stateEventFlow.test {
            assertEquals(StateEvent.Loading, awaitItem())
            assertEquals(StateEvent.ClearSuccess, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { repository.clearAllDb() }
    }

    @Test
    fun `test addCurrencyInfo emits state events successfully`() = runTest {
        coEvery {
            repository.insertCurrency(
                testCurrencyUiModel.toCurrencyInfoModel(),
                1,
            )
        } returns Unit

        viewModel.addCurrencyInfo(1, testCurrencyUiModel)

        viewModel.stateEventFlow.test {
            assertEquals(StateEvent.Loading, awaitItem())
            assertEquals(StateEvent.AddSuccess, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) {
            repository.insertCurrency(
                testCurrencyUiModel.toCurrencyInfoModel(),
                1,
            )
        }
    }

    @Test
    fun `test navigateAction emits UI action`() = runTest {
        viewModel = MainViewModel(repository)
        val action = MainAction.ShowListAction(1)

        viewModel.navigateAction(action)

        viewModel.uiActionFlow.test {
            assertEquals(action, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    companion object {
        val testCurrencyGroupModelList = listOf(
            CurrencyGroupModel(id = 1, groupName = "Group A"),
            CurrencyGroupModel(id = 2, groupName = "Group B"),
        )

        val testCurrencyGroupUiModelList = listOf(
            CurrencyGroupUiModel(id = 1, groupName = "Group A"),
            CurrencyGroupUiModel(id = 2, groupName = "Group B"),
        )

        val testCurrencyUiModel = CurrencyInfoUiModel(
            id = "usd",
            name = "United States Dollar",
            symbol = "$",
            code = "USD",
        )
    }
}
