package com.example.mediator.currencyinfo.presentation.mapper

import com.example.mediator.currencyinfo.domain.model.CurrencyGroupModel
import com.example.mediator.currencyinfo.domain.model.CurrencyInfoModel
import com.example.mediator.currencyinfo.presentation.uimodel.CurrencyGroupUiModel
import com.example.mediator.currencyinfo.presentation.uimodel.CurrencyInfoUiModel
import kotlinx.collections.immutable.toImmutableList
import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyUiModelMapperTest {

    @Test
    fun `test CurrencyInfoModel toCurrencyInfoUiModel maps USD correctly`() {
        // Given
        val usdModel = CurrencyInfoModel(
            id = "usd",
            name = "United States Dollar",
            symbol = "$",
            code = "USD",
        )
        val expected = CurrencyInfoUiModel(
            id = "usd",
            name = "United States Dollar",
            symbol = "$",
            code = "USD",
        )

        // When
        val actual = usdModel.toCurrencyInfoUiModel()

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `test CurrencyInfoUiModel toCurrencyInfoModel maps USD correctly`() {
        // Given
        val usdUi = CurrencyInfoUiModel(
            id = "usd",
            name = "United States Dollar",
            symbol = "$",
            code = "USD",
        )
        val expected = CurrencyInfoModel(
            id = "usd",
            name = "United States Dollar",
            symbol = "$",
            code = "USD",
        )

        // When
        val actual = usdUi.toCurrencyInfoModel()

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `test CurrencyGroupModel toCurrencyGroupUiModel maps group with USD correctly`() {
        // Given
        val usd = CurrencyInfoModel(
            id = "usd",
            name = "United States Dollar",
            symbol = "$",
            code = "USD",
        )

        val eur = CurrencyInfoModel(
            id = "eu",
            name = "Euro",
            symbol = "€",
            code = "EUR",
        )

        val groupModel = CurrencyGroupModel(
            id = 1,
            groupName = "Group A",
            currencyInfoModels = listOf(usd, eur),
        )
        val expected = CurrencyGroupUiModel(
            id = 1,
            groupName = "Group A",
            currencyUiModels = listOf(
                CurrencyInfoUiModel(
                    id = "usd",
                    name = "United States Dollar",
                    symbol = "$",
                    code = "USD",
                ),
                CurrencyInfoUiModel(
                    id = "eu",
                    name = "Euro",
                    symbol = "€",
                    code = "EUR",
                ),
            ).toImmutableList(),
        )

        // When
        val actual = groupModel.toCurrencyGroupUiModel()

        // Then
        assertEquals(expected, actual)
    }
}
