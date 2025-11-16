package com.example.feature.currencyinfo.presentation.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feature.currencyinfo.R
import com.example.mediator.currencyinfo.presentation.uimodel.CurrencyGroupUiModel
import com.example.mediator.currencyinfo.presentation.uimodel.CurrencyInfoUiModel

@Composable
fun AddCurrencyDialog(
    currencyGroupUiModels: List<CurrencyGroupUiModel>,
    onDismiss: () -> Unit,
    onSave: (groupId: Int, currencyInfoUiModel: CurrencyInfoUiModel) -> Unit,
) {
    var id by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var symbol by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    var selectedGroup by remember { mutableStateOf<CurrencyGroupUiModel?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.add_currency_title)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                OutlinedTextField(
                    value = id,
                    onValueChange = { id = it },
                    label = { Text(stringResource(R.string.label_id)) }
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.label_name)) }
                )

                OutlinedTextField(
                    value = symbol,
                    onValueChange = { symbol = it },
                    label = { Text(stringResource(R.string.label_symbol)) }
                )

                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it },
                    label = { Text(stringResource(R.string.label_code)) }
                )

                Box {
                    OutlinedTextField(
                        value = selectedGroup?.groupName ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.label_group_type)) },
                        trailingIcon = {
                            IconButton(onClick = { expanded = true }) {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                            }
                        }
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        currencyGroupUiModels.forEach { group ->
                            DropdownMenuItem(
                                text = { Text(group.groupName) },
                                onClick = {
                                    selectedGroup = group
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    selectedGroup?.let {
                        onSave(
                            it.id,
                            CurrencyInfoUiModel(
                                name = name,
                                symbol = symbol,
                                code = code
                            )
                        )
                    }
                }
            ) {
                Text(stringResource(R.string.cta_save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cta_cancel))
            }
        }
    )
}

@Preview
@Composable
fun PreviewAddCurrencyDialog() {
    AddCurrencyDialog(
        currencyGroupUiModels = listOf(
            CurrencyGroupUiModel(
                id = 0,
                groupName = "Test Group A"
            ),
        ),
        onDismiss = {},
        onSave = { _, _ -> },
    )
}
