package com.example.feature.currencyinfo.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.core.kttestutils.MainDispatcherRule
import com.example.feature.currencyinfo.testCurrencyInfoModelGroup1
import com.example.feature.currencyinfo.testCurrencyInfoModelList
import com.example.feature.currencyinfo.testGroupModel
import com.example.mediator.currencyinfo.domain.CurrencyRepository
import com.example.mediator.currencyinfo.presentation.uimodel.CurrencyInfoUiModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: CurrencyRepository
    private lateinit var viewModel: CurrencyViewModel
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        savedStateHandle = mockk<SavedStateHandle> {
            every { get<Int>(ARG_GROUP_ID) } returns 1
        }
    }

    @Test
    fun `test groupInfoFlow`() = runTest {
        every { repository.getGroupInfo(1) } returns flowOf(testGroupModel)

        initViewModel()

        viewModel.groupInfoFlow.test {
            val actual1 = awaitItem()
            assertNull(actual1)

            val actual2 = awaitItem()
            assertNotNull(actual2)
            assertEquals("Group A", actual2?.groupName)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test currenciesFlow emit all when search is empty`() = runTest {
        every { repository.getCurrenciesByGroup(1) } returns flowOf(testCurrencyInfoModelList)

        initViewModel()

        viewModel.currenciesFlow.test {
            val actual1 = awaitItem()
            assertNull(actual1)

            val actual2 = awaitItem()
            assertEquals(2, actual2?.size)
            assertEquals(testCurrencyInfoUiModelList, actual2)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test currenciesFlow emit all when search is not empty`() = runTest {
        coEvery { repository.searchCurrencies("MCO", 1) } returns flowOf(
            listOf(
                testCurrencyInfoModelGroup1,
            ),
        )

        initViewModel()

        viewModel.searchCurrencies("MCO")
        viewModel.currenciesFlow.test {
            val actual1 = awaitItem()
            assertNull(actual1)

            val actual2 = awaitItem()
            assertEquals(1, actual2?.size)
            assertEquals(testCurrencyInfoUiModel1, actual2?.get(0))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test searchCurrencies is called`() = runBlocking {
        initViewModel()

        // Initially empty
        assertEquals("", viewModel.searchKeywordFlow.first())

        // Call search
        viewModel.searchCurrencies("MCO")
        assertEquals("MCO", viewModel.searchKeywordFlow.first())
    }

    @Test
    fun `test searchCurrencies and then clearSearch`() = runBlocking {
        initViewModel()

        viewModel.searchCurrencies("Ethereum")
        assertEquals("Ethereum", viewModel.searchKeywordFlow.first())

        viewModel.clearSearch()
        assertEquals("", viewModel.searchKeywordFlow.first())
    }

    private fun initViewModel() {
        viewModel = CurrencyViewModel(repository, savedStateHandle)
    }

    companion object {
        const val ARG_GROUP_ID = "ARG_GROUP_ID"

        private val testCurrencyInfoUiModel1 = CurrencyInfoUiModel(
            id = "mco",
            name = "MCO Token",
            symbol = "MCO",
            code = "MCO",
        )

        private val testCurrencyInfoUiModel2 = CurrencyInfoUiModel(
            id = "usd",
            name = "United States Dollar",
            symbol = "$",
            code = "USD",
        )

        private val testCurrencyInfoUiModelList = listOf(
            testCurrencyInfoUiModel1,
            testCurrencyInfoUiModel2,
        ).toImmutableList()
    }
}
