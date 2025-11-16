package com.example.democurrency.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.democurrency.R
import com.example.mediator.currencyinfo.presentation.uimodel.CurrencyGroupUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun ActionButtonsGroupView(
    currencyGroupUiModels: ImmutableList<CurrencyGroupUiModel>,
    modifier: Modifier = Modifier,
    onInitDb: () -> Unit = {},
    onClearDb: () -> Unit = {},
    onInsertDb: () -> Unit = {},
    onGroupClick: (Int) -> Unit = {},
    onDisplayAlls: () -> Unit = {},
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "DemoCurrency",
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0XFF6200EE))
                .padding(horizontal = 16.dp, vertical = 8.dp),
        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onInitDb() }
            ) {
                Text(stringResource(R.string.cta_init_data))
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onClearDb() }
            ) {
                Text(stringResource(R.string.cta_clear_data))
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onInsertDb() }
            ) {
                Text(stringResource(R.string.cta_insert_data))
            }

            currencyGroupUiModels.forEach { group ->
                Button(
                    onClick = { onGroupClick(group.id) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(group.groupName)
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onDisplayAlls() }
            ) {
                Text(stringResource(R.string.cta_display_all))
            }
        }
    }
}

@Preview
@Composable
fun PreviewActionButtonsGroupView() {
    ActionButtonsGroupView(
        currencyGroupUiModels = listOf(
            CurrencyGroupUiModel(
                id = 0,
                groupName = "Abc",
            )
        ).toImmutableList()
    )
}
