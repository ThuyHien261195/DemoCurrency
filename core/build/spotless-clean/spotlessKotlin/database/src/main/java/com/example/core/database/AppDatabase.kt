package com.example.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.database.dao.CurrencyDao
import com.example.core.database.model.CurrencyGroupTypeEntity
import com.example.core.database.model.CurrencyInfoEntity

@Database(
    entities = [
        CurrencyGroupTypeEntity::class,
        CurrencyInfoEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
}
