package com.example.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_group_type_entity")
data class CurrencyGroupTypeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
)