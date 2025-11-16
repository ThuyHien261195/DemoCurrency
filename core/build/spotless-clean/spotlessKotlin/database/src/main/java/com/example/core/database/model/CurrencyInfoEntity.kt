package com.example.core.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "currency_info_entity",
    foreignKeys = [
        ForeignKey(
            entity = CurrencyGroupTypeEntity::class,
            parentColumns = ["id"],
            childColumns = ["groupId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("groupId")],
)
class CurrencyInfoEntity(
    @PrimaryKey
    val id: String = "",
    val name: String,
    val symbol: String,
    val code: String,
    val groupId: Int,
)
