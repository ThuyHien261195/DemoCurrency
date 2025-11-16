package com.example.mediator.currencyinfo.presentation.mapper

import com.example.mediator.currencyinfo.domain.model.CurrencyGroupModel
import com.example.mediator.currencyinfo.domain.model.CurrencyInfoModel
import com.example.mediator.currencyinfo.presentation.uimodel.CurrencyGroupUiModel
import com.example.mediator.currencyinfo.presentation.uimodel.CurrencyInfoUiModel
import kotlinx.collections.immutable.toImmutableList

fun CurrencyInfoModel.toCurrencyInfoUiModel(): CurrencyInfoUiModel {
    return CurrencyInfoUiModel(
        id = id,
        name = name,
        symbol = symbol,
        code = code,
    )
}

fun CurrencyInfoUiModel.toCurrencyInfoModel(): CurrencyInfoModel {
    return CurrencyInfoModel(
        name = name,
        symbol = symbol,
        code = code,
    )
}

fun CurrencyGroupModel.toCurrencyGroupUiModel(): CurrencyGroupUiModel {
    return CurrencyGroupUiModel(
        id = id,
        groupName = groupName,
        currencyUiModels = currencyInfoModels.map {
            it.toCurrencyInfoUiModel()
        }.toImmutableList(),
    )
}

