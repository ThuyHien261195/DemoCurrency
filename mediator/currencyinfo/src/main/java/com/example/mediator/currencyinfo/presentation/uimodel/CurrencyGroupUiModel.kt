package com.example.mediator.currencyinfo.presentation.uimodel

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class CurrencyGroupUiModel(
    val id: Int = 0,
    val groupName: String = "",
    val currencyUiModels: ImmutableList<CurrencyInfoUiModel> = persistentListOf(),
)