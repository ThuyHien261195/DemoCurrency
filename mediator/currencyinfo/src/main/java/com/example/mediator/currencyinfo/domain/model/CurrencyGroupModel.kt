package com.example.mediator.currencyinfo.domain.model

data class CurrencyGroupModel(
    val id: Int = 0,
    val groupName: String = "",
    val currencyInfoModels: List<CurrencyInfoModel> = emptyList(),
)