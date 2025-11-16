package com.example.feature.currencyinfo.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feature.currencyinfo.R
import com.example.mediator.currencyinfo.presentation.uimodel.CurrencyInfoUiModel
import kotlinx.collections.immutable.ImmutableList


@Composable
fun CurrencyListView(
    currencies: ImmutableList<CurrencyInfoUiModel>?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        if (currencies == null) {
            LoadingView()
        } else if (currencies.isEmpty()) {
            EmptyCurrencyListView()
        } else {
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
}

@Composable
fun EmptyCurrencyListView(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Surface(
            shape = CircleShape,
            color = Color.Transparent,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_no_results),
                contentDescription = null,
                modifier = Modifier.size(96.dp),
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.msg_no_results),
            color = Color(0xFF424242),
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.description_no_results),
            color = Color(0xFF9E9E9E),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview
@Composable
fun PreviewEmptyCurrencyListView() {
    EmptyCurrencyListView()
}