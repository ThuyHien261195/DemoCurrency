package com.example.feature.currencyinfo.data

import android.content.Context
import com.example.core.database.AppDatabase
import com.example.core.database.dao.CurrencyDao
import com.example.core.database.model.CurrencyGroupTypeEntity
import com.example.core.database.model.CurrencyInfoEntity
import com.example.feature.currencyinfo.testCurrencyGroupModelList
import com.example.feature.currencyinfo.testCurrencyInfoModelGroup1
import com.example.feature.currencyinfo.testCurrencyInfoModelGroup2
import com.example.feature.currencyinfo.testGroupModel
import com.example.mediator.currencyinfo.domain.model.CurrencyGroupModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyRepositoryImplTest {

    private lateinit var repository: CurrencyRepositoryImpl
    private lateinit var currencyDao: CurrencyDao
    private val context = mockk<Context>(relaxed = true)
    private val appDatabase = mockk<AppDatabase>(relaxed = true)
    val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        currencyDao = mockk()
        repository = CurrencyRepositoryImpl(
            appDatabase = appDatabase,
            currencyDao = currencyDao,
            applicationContext = context,
            testDispatcher,
        )
    }

    @Test
    fun `test getAllGroupInfos`() = runTest {
        coEvery { currencyDao.getAllGroups() } returns flowOf(testGroupEntityList)

        val actual = repository.getAllGroupInfos().first()

        assertEquals(2, actual.size)
        assertEquals(testCurrencyGroupModelList, actual)
    }

    @Test
    fun `test getGroupInfo`() = runTest {
        coEvery { currencyDao.getGroupInfo(1) } returns flowOf(testGroupEntity)

        val result = repository.getGroupInfo(1).first()
        assertEquals(testGroupModel, result)
    }

    @Test
    fun `test insertGroups`() = runTest {
        val groups = listOf(CurrencyGroupModel(1, "Crypto"))
        coEvery { currencyDao.insertGroups(any()) } just Runs

        repository.insertGroups(groups)

        coVerify {
            currencyDao.insertGroups(
                match { it.size == 1 && it[0].name == "Crypto" },
            )
        }
    }

    @Test
    fun `test insertCurrency`() = runTest {
        coEvery { currencyDao.insertCurrency(any()) } just Runs

        repository.insertCurrency(testCurrencyInfoModelGroup1, groupId = 1)

        coVerify { currencyDao.insertCurrency(match { it.name == "MCO Token" && it.groupId == 1 }) }
    }

    @Test
    fun `test insertCurrencies`() = runTest {
        val currencies = listOf(
            testCurrencyInfoModelGroup1,
            testCurrencyInfoModelGroup2,
        )
        coEvery { currencyDao.insertCurrencies(any()) } just Runs

        repository.insertCurrencies(currencies, groupId = 1)

        coVerify { currencyDao.insertCurrencies(match { it.size == 2 }) }
    }

    @Test
    fun `test getCurrenciesByGroup`() = runTest {
        val currencies = listOf(testCurrencyInfoEntityGroup1)
        coEvery { currencyDao.getCurrenciesByGroup(1) } returns flowOf(currencies)

        val result = repository.getCurrenciesByGroup(1).first()
        assertEquals(1, result.size)
        assertEquals(testCurrencyInfoModelGroup1, result[0])
    }

    @Test
    fun `test searchCurrencies`() = runTest {
        val currencies = listOf(
            testCurrencyInfoEntityGroup1,
            testCurrencyInfoEntityGroup2,
        )
        coEvery { currencyDao.searchCurrencies("MCO", 1) } returns flowOf(
            currencies.filter { it.name.contains("MCO") || it.symbol.startsWith("MCO") },
        )

        val result = repository.searchCurrencies("MCO", 1).first()
        assertEquals(1, result.size)
        assertEquals("MCO Token", result[0].name)
    }

    @Test
    fun `test clearAllDb`() = runTest {
        coEvery { currencyDao.clearCurrencyInfo() } just Runs

        repository.clearAllDb()

        coVerify { currencyDao.clearCurrencyInfo() }
    }

    companion object {
        private val testGroupEntity = CurrencyGroupTypeEntity(id = 1, name = "Group A")
        private val testGroupEntityList = listOf(
            testGroupEntity,
            CurrencyGroupTypeEntity(id = 2, name = "Group B"),
        )

        private val testCurrencyInfoEntityGroup1 = CurrencyInfoEntity(
            id = "mco",
            name = "MCO Token",
            symbol = "MCO",
            code = "MCO",
            groupId = 1,
        )

        private val testCurrencyInfoEntityGroup2 = CurrencyInfoEntity(
            id = "usd",
            name = "United States Dollar",
            symbol = "$",
            code = "USD",
            groupId = 2,
        )
    }
}
