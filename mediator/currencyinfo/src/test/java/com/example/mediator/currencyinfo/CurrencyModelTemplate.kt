package com.example.mediator.currencyinfo

import com.example.core.database.model.CurrencyGroupTypeEntity
import com.example.core.database.model.CurrencyInfoEntity
import com.example.mediator.currencyinfo.domain.model.CurrencyGroupModel
import com.example.mediator.currencyinfo.domain.model.CurrencyInfoModel
import com.example.mediator.currencyinfo.presentation.uimodel.CurrencyGroupUiModel
import com.example.mediator.currencyinfo.presentation.uimodel.CurrencyInfoUiModel

// Database Model
private val testGroupList = listOf(
    CurrencyGroupTypeEntity(id = 1, name = "Group A"),
    CurrencyGroupTypeEntity(id = 2, name = "Group B"),
)

private val testCurrencyGroup1 = CurrencyInfoEntity(
    id = "mco",
    name = "MCO Token",
    symbol = "MCO",
    code = "MCO",
    groupId = 1,
)

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

// UiModel
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
