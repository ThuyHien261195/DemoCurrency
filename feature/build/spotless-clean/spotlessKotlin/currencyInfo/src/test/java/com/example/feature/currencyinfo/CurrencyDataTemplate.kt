package com.example.feature.currencyinfo

import com.example.mediator.currencyinfo.domain.model.CurrencyGroupModel
import com.example.mediator.currencyinfo.domain.model.CurrencyInfoModel

// Domain Model
val testGroupModel = CurrencyGroupModel(id = 1, groupName = "Group A")

val testCurrencyGroupModelList = listOf(
    CurrencyGroupModel(id = 1, groupName = "Group A"),
    CurrencyGroupModel(id = 2, groupName = "Group B"),
)

val testCurrencyInfoModelGroup1 = CurrencyInfoModel(
    id = "mco",
    name = "MCO Token",
    symbol = "MCO",
    code = "MCO",
)

val testCurrencyInfoModelGroup2 = CurrencyInfoModel(
    id = "usd",
    name = "United States Dollar",
    symbol = "$",
    code = "USD",
)

val testCurrencyInfoModelList = listOf(
    testCurrencyInfoModelGroup1,
    testCurrencyInfoModelGroup2,
)
