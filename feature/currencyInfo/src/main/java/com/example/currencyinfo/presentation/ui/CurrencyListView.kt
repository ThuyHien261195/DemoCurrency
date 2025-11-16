package com.example.currencyinfo.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mediator.currencyinfo.presentation.uimodel.CurrencyInfoUiModel
import kotlinx.collections.immutable.ImmutableList


@Composable
fun CurrencyListView(
    currencies: ImmutableList<CurrencyInfoUiModel>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(currencies.size) { index ->
                CurrencyItemView(currencies[index])

                if (index < currencies.lastIndex) {
                    HorizontalDivider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(
                            start = 70.dp,
                            end = 0.dp,
                        )
                    )
                }
            }
        }
    }
}
