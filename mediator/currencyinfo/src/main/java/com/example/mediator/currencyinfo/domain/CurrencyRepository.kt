package com.example.mediator.currencyinfo.domain

import com.example.core.database.model.CurrencyInfoEntity
import com.example.mediator.currencyinfo.domain.model.CurrencyGroupModel
import com.example.mediator.currencyinfo.domain.model.CurrencyInfoModel
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    fun getAllGroupInfos(): Flow<List<CurrencyGroupModel>>

    fun getGroupInfo(groupId: Int?): Flow<CurrencyGroupModel?>

    suspend fun insertGroups(groups: List<CurrencyGroupModel>)

    fun getCurrenciesByGroup(groupId: Int?): Flow<List<CurrencyInfoModel>>

    suspend fun insertCurrencies(currencies: List<CurrencyInfoModel>, groupId: Int)

    suspend fun insertCurrency(currency: CurrencyInfoModel, groupId: Int)

    suspend fun clearAllDb()

    suspend fun initData()

    fun searchCurrencies(keyword: String, groupId: Int?): Flow<List<CurrencyInfoModel>>
}