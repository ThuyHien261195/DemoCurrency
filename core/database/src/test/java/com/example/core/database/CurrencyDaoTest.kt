package com.example.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.core.database.dao.CurrencyDao
import com.example.core.database.model.CurrencyGroupTypeEntity
import com.example.core.database.model.CurrencyInfoEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CurrencyDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: CurrencyDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.currencyDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `test insertGroups and getAllGroups and getGroupInfo`() = runBlocking {
        dao.insertGroups(testGroupList)

        // Verify getAllGroups returns both in any order (we'll assert contains)
        val actual1 = dao.getAllGroups().first()
        assertEquals(testGroupList, actual1)

        // Verify getGroupInfo returns correct single entity
        val actual2 = dao.getGroupInfo(1).first()
        assertEquals(1, actual2?.id)
        assertEquals("Group A", actual2?.name)

        // Verify getGroupInfo with non-existing id returns null
        val actual3 = dao.getGroupInfo(3).first()
        assertNull(actual3)
    }

    @Test
    fun `test insertCurrency and getCurrenciesByGroup`() = runBlocking {
        dao.insertGroups(testGroupList)
        dao.insertCurrency(testCurrencyGroup1)

        // Verify group 1 should have two items
        val actual1 = dao.getCurrenciesByGroup(1).first()
        assertEquals(1, actual1.size)
        assertEquals(testCurrencyGroup1.id, actual1[0].id)

        // Verify getCurrenciesByGroup(null) should return all currencies
        val actual2 = dao.getCurrenciesByGroup(null).first()
        assertEquals(1, actual2.size)
    }

    @Test
    fun `test insertCurrencies and getCurrenciesByGroup`() = runBlocking {
        dao.insertGroups(testGroupList)
        dao.insertCurrencies(listOf(testCurrencyGroup1, testCurrencyGroup2))

        // Verify getCurrenciesByGroup for groupId=1 returns single item
        val actual1 = dao.getCurrenciesByGroup(1).first()
        assertEquals(1, actual1.size)
        assertEquals(testCurrencyGroup1.id, actual1[0].id)

        // Verify getCurrenciesByGroup(null) should return all currencies
        val actual2 = dao.getCurrenciesByGroup(null).first()
        assertEquals(2, actual2.size)
        assertEquals(testCurrencyGroup1.id, actual2[0].id)
        assertEquals(testCurrencyGroup2.id, actual2[1].id)
    }

    @Test
    fun `test searchCurrencies appliesKeywordAndGroupFilter`() = runBlocking {
        dao.insertGroups(testGroupList)
        dao.insertCurrencies(testSearchingCurrencyList)

        // Search for "MCO" with groupId = 1
        val actual1 = dao.searchCurrencies("MCO", 1).first()
        assertEquals(2, actual1.size)
        assertTrue(actual1.any { it.id == "ac" })
        assertTrue(actual1.any { it.id == "mco" })

        // Search for "MCO" with groupId = null
        val actual2 = dao.searchCurrencies("MCO", null).first()
        assertEquals(3, actual2.size)
        assertTrue(actual2.any { it.id == "ac" })
        assertTrue(actual2.any { it.id == "mco" })
        assertTrue(actual2.any { it.id == "sx" })
    }

    @Test
    fun `test searchCurrencies byName`() = runBlocking {
        dao.insertGroups(testGroupList)
        dao.insertCurrencies(testSearchingCurrencyList)

        // Search for "MCO"
        val actual1 = dao.searchCurrencies("MCO", null).first()
        assertEquals(3, actual1.size)
        // Verify search name with prefix is "MCO"
        assertTrue(actual1.any { it.id == "ac" })
        // Verify search name with start word is "MCO"
        assertTrue(actual1.any { it.id == "mco" })
        // Verify search name with a ‘ ’ (space) prefixed to the "MCO"
        assertTrue(actual1.any { it.id == "sx" })

        // Search should match case-insensitively
        val actual2 = dao.searchCurrencies("mco", null).first()
        assertEquals(3, actual2.size)
    }

    @Test
    fun `test searchCurrencies bySymbol`() = runBlocking {
        dao.insertGroups(testGroupList)
        dao.insertCurrencies(testSearchingCurrencyList)

        // Verify symbol with prefix search 'SB'
        val actual = dao.searchCurrencies("SB", 2).first()
        assertTrue(actual.any { it.id == "beta" })
    }

    @Test
    fun `test clearCurrencyInfo removesAllCurrencies`() = runBlocking {
        // Insert some currencies
        dao.insertGroups(testGroupList)
        dao.insertCurrencies(listOf(testCurrencyGroup1, testCurrencyGroup2))

        // Ensure inserted
        val before = dao.getCurrenciesByGroup(null).first()
        assertEquals(2, before.size)

        // Clear table
        dao.clearCurrencyInfo()

        // Verify after clear, getCurrenciesByGroup(null) returns empty list
        val after = dao.getCurrenciesByGroup(null).first()
        assertTrue(after.isEmpty())
    }

    companion object {
        private val testGroupList = listOf(
            CurrencyGroupTypeEntity(id = 1, name = "Group A"),
            CurrencyGroupTypeEntity(id = 2, name = "Group B"),
        )

        private val testSearchingCurrencyList = listOf(
            CurrencyInfoEntity(
                id = "ac",
                name = "MCOAlpha Coin",
                symbol = "AC",
                code = "AC",
                groupId = 1,
            ),
            CurrencyInfoEntity(
                id = "mco",
                name = "MCO Token",
                symbol = "MCO",
                code = "MCO",
                groupId = 1,
            ),
            CurrencyInfoEntity(
                id = "beta",
                name = "Beta Coin",
                symbol = "SBBC",
                code = "BC",
                groupId = 2,
            ),
            CurrencyInfoEntity(
                id = "sx",
                name = "Something MCO Else",
                symbol = "SX",
                code = "SX",
                groupId = 2,
            ),
        )

        private val testCurrencyGroup1 = CurrencyInfoEntity(
            id = "mco",
            name = "MCO Token",
            symbol = "MCO",
            code = "MCO",
            groupId = 1,
        )

        private val testCurrencyGroup2 = CurrencyInfoEntity(
            id = "usd",
            name = "United States Dollar",
            symbol = "$",
            code = "USD",
            groupId = 2,
        )
    }
}
