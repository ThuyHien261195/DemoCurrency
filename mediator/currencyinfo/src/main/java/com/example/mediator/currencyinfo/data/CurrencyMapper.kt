package com.example.mediator.currencyinfo.data

import com.example.core.database.model.CurrencyGroupTypeEntity
import com.example.core.database.model.CurrencyGroupWithCurrencies
import com.example.core.database.model.CurrencyInfoEntity
import com.example.mediator.currencyinfo.domain.model.CurrencyGroupModel
import com.example.mediator.currencyinfo.domain.model.CurrencyInfoModel

fun CurrencyInfoEntity.toCurrencyInfoModel() = CurrencyInfoModel(
    id = this.id,
    name = this.name,
    symbol = this.symbol,
    code = this.code,
)

fun CurrencyInfoModel.toCurrencyInfoEntity(groupId: Int) = CurrencyInfoEntity(
    id = this.id,
    name = this.name,
    symbol = this.symbol,
    code = this.code,
    groupId = groupId,
)

fun CurrencyGroupWithCurrencies.toCurrencyGroupModel() = CurrencyGroupModel(
    id = this.group.id,
    groupName = this.group.name,
    currencyInfoModels = this.currencies.map { it.toCurrencyInfoModel() },
)

fun CurrencyGroupTypeEntity.toCurrencyGroupModel() = CurrencyGroupModel(
    id = this.id,
    groupName = this.name,
)

fun CurrencyGroupModel.toCurrencyGroupTypeEntity() = CurrencyGroupTypeEntity(
    id = this.id,
    name = this.groupName,
)

