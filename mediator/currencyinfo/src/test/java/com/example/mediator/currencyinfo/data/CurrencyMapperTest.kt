package com.example.mediator.currencyinfo.data

import com.example.core.database.model.CurrencyGroupTypeEntity
import com.example.core.database.model.CurrencyInfoEntity
import com.example.mediator.currencyinfo.domain.model.CurrencyGroupModel
import com.example.mediator.currencyinfo.domain.model.CurrencyInfoModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyMapperTest {

    @Test
    fun `CurrencyInfoEntity toCurrencyInfoModel maps all fields`() {
        // Given
        val entity = CurrencyInfoEntity(
            id = "usd",
            name = "United States Dollar",
            symbol = "$",
            code = "USD",
            groupId = 1,
        )
        val expected = CurrencyInfoModel(
            id = "usd",
            name = "United States Dollar",
            symbol = "$",
            code = "USD",
        )

        // When
        val actual = entity.toCurrencyInfoModel()

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `test CurrencyInfoModel toCurrencyInfoEntity maps all fields and sets groupId`() {
        // Given
        val model = CurrencyInfoModel(
            id = "usd",
            name = "United States Dollar",
            symbol = "$",
            code = "USD",
        )
        val groupId = 1
        val expected = CurrencyInfoEntity(
            id = "usd",
            name = "United States Dollar",
            symbol = "$",
            code = "USD",
            groupId = 1,
        )

        // When
        val actual = model.toCurrencyInfoEntity(groupId)

        // Then
        assertEquals(expected.id, actual.id)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.symbol, actual.symbol)
        assertEquals(expected.code, actual.code)
        assertEquals(expected.groupId, actual.groupId)
    }

    @Test
    fun `test CurrencyGroupTypeEntity toCurrencyGroupModel maps all fields`() {
        // Given
        val entity = CurrencyGroupTypeEntity(
            id = 1,
            name = "Group A",
        )
        val expected = CurrencyGroupModel(
            id = 1,
            groupName = "Group A",
        )

        // When
        val actual = entity.toCurrencyGroupModel()

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `test CurrencyGroupModel toCurrencyGroupTypeEntity maps all fields`() {
        // Given
        val model = CurrencyGroupModel(
            id = 1,
            groupName = "Group A",
        )
        val expected = CurrencyGroupTypeEntity(
            id = 1,
            name = "Group A",
        )

        // When
        val entity = model.toCurrencyGroupTypeEntity()

        // Then
        assertEquals(expected, entity)
    }
}
