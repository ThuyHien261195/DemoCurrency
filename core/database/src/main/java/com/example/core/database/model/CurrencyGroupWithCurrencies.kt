package com.example.core.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class CurrencyGroupWithCurrencies(
    @Embedded val group: CurrencyGroupTypeEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    )
    val currencies: List<CurrencyInfoEntity>
)