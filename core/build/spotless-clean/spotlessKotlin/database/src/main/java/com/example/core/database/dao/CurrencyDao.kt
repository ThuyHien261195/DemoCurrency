package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.database.model.CurrencyGroupTypeEntity
import com.example.core.database.model.CurrencyInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: CurrencyGroupTypeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroups(groups: List<CurrencyGroupTypeEntity>)

    @Query("SELECT * FROM currency_group_type_entity")
    fun getAllGroups(): Flow<List<CurrencyGroupTypeEntity>>

    @Query("SELECT * FROM currency_group_type_entity WHERE id = :groupId")
    fun getGroupInfo(groupId: Int?): Flow<CurrencyGroupTypeEntity?>

    @Query("SELECT * FROM currency_info_entity")
    fun getAllCurrencies(): Flow<List<CurrencyInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencies: List<CurrencyInfoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currency: CurrencyInfoEntity): Long

    @Query("SELECT * FROM currency_info_entity WHERE :groupId IS NULL OR groupId = :groupId")
    fun getCurrenciesByGroup(groupId: Int?): Flow<List<CurrencyInfoEntity>>

    @Query("DELETE FROM currency_info_entity")
    suspend fun clearCurrencyInfo()

    @Query("DELETE FROM currency_group_type_entity")
    suspend fun clearCurrencyGroup()

    @Query(
        """
    SELECT *
    FROM currency_info_entity
    WHERE
        (
            LOWER(name) LIKE LOWER(:keyword || '%')
            OR LOWER(name) LIKE LOWER('% ' || :keyword || '%')
            OR LOWER(symbol) LIKE LOWER(:keyword || '%')
        )
        AND (:groupId IS NULL OR groupId = :groupId)
        """,
    )
    fun searchCurrencies(keyword: String, groupId: Int?): Flow<List<CurrencyInfoEntity>>
}
