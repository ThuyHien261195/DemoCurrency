package com.example.currencyinfo.data

import android.content.Context
import androidx.room.withTransaction
import com.example.core.database.AppDatabase
import com.example.core.database.dao.CurrencyDao
import com.example.mediator.currencyinfo.data.toCurrencyGroupModel
import com.example.mediator.currencyinfo.data.toCurrencyGroupTypeEntity
import com.example.mediator.currencyinfo.data.toCurrencyInfoEntity
import com.example.mediator.currencyinfo.data.toCurrencyInfoModel
import com.example.mediator.currencyinfo.domain.CurrencyRepository
import com.example.mediator.currencyinfo.domain.model.CurrencyGroupModel
import com.example.mediator.currencyinfo.domain.model.CurrencyInfoModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val currencyDao: CurrencyDao,
    @ApplicationContext private val applicationContext: Context,
) : CurrencyRepository {

    override fun getAllGroupInfos(): Flow<List<CurrencyGroupModel>> = currencyDao.getAllGroups().map { data ->
        data.map { it.toCurrencyGroupModel() }
    }

    override fun getGroupInfo(groupId: Int?): Flow<CurrencyGroupModel?> = currencyDao.getGroupInfo(groupId = groupId).map {
        it?.toCurrencyGroupModel()
    }

    override suspend fun insertGroups(groups: List<CurrencyGroupModel>) {
        currencyDao.insertGroups(groups.map { it.toCurrencyGroupTypeEntity() })
    }

    override fun getCurrenciesByGroup(groupId: Int?): Flow<List<CurrencyInfoModel>> = currencyDao.getCurrenciesByGroup(groupId).map { data ->
        data.map { it.toCurrencyInfoModel() }
    }

    override suspend fun insertCurrencies(currencies: List<CurrencyInfoModel>, groupId: Int) {
        currencyDao.insertCurrencies(
            currencies.map {
                it.toCurrencyInfoEntity(groupId)
            },
        )
    }

    override suspend fun insertCurrency(currency: CurrencyInfoModel, groupId: Int) {
        currencyDao.insertCurrency(currency.toCurrencyInfoEntity(groupId))
    }

    override suspend fun clearAllDb() {
        currencyDao.clearCurrencyInfo()
    }

    // Read JSON from assets and parse to model
    override suspend fun initData() {
        withContext(Dispatchers.IO) {
            appDatabase.withTransaction {
                val groups = listOf(
                    CurrencyGroupModel(id = CRYPTO_GROUP_ID, groupName = "Crypto Currency"),
                    CurrencyGroupModel(id = FIAT_GROUP_ID, groupName = "Fiat Currency"),
                )
                insertGroups(groups)
                val cryptoCurrencies = loadDataFromJson(CRYPTO_CURRENCIES_FILE_NAME)
                val fiatCurrencies = loadDataFromJson(FIAT_CURRENCIES_FILE_NAME)
                insertCurrencies(cryptoCurrencies, groups[0].id)
                insertCurrencies(fiatCurrencies, groups[1].id)
            }
        }
    }

    private suspend fun loadDataFromJson(fileName: String): List<CurrencyInfoModel> = withContext(Dispatchers.IO) {
        try {
            val jsonString = applicationContext.assets.open(fileName)
                .bufferedReader()
                .use { it.readText() }

            // Parse JSON with Gson
            val type = object : TypeToken<List<CurrencyInfoModel>>() {}.type
            Gson().fromJson(jsonString, type)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override fun searchCurrencies(keyword: String, groupId: Int?): Flow<List<CurrencyInfoModel>> = currencyDao.searchCurrencies(keyword, groupId).map { data ->
        data.map { it.toCurrencyInfoModel() }
    }

    companion object {
        private const val CRYPTO_CURRENCIES_FILE_NAME = "crypto_currencies.json"
        private const val FIAT_CURRENCIES_FILE_NAME = "fiat_currencies.json"
        private const val CRYPTO_GROUP_ID = 1
        private const val FIAT_GROUP_ID = 2
    }
}
