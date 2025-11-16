package com.example.currencyinfo.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feature.currencyinfo.R

@Composable
fun CurrencySearchBar(
    groupName: String?,
    onSearchKeywordChanged: (String) -> Unit,
    onBack: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val iconTint = Color(0xFF585E66)
    var text by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = Color(0xFFF6F9FA), shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = iconTint,
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                    onSearchKeywordChanged(it)
                },
                singleLine = true,
                textStyle = TextStyle(
                    color = Color(0xFF3C4852),
                    fontSize = 20.sp // large character like screenshot
                ),
                cursorBrush = SolidColor(Color(0xFF3C4852)),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        val y = size.height
                        drawLine(
                            color = Color(0xFF9AA4AB).copy(alpha = 0.9f),
                            strokeWidth = 1.dp.toPx(),
                            start = Offset(0f, y),
                            end = Offset(size.width, y)
                        )
                    }
                    .padding(vertical = 8.dp)
            ) { innerTextField ->
                if (text.isEmpty()) {
                    Text(
                        text = if (groupName != null) {
                            stringResource(R.string.label_search_in, groupName)
                        } else {
                            stringResource(R.string.label_search_all)
                        },
                        style = TextStyle(color = Color(0xFF9AA4AB), fontSize = 18.sp)
                    )
                }
                innerTextField()
            }
        }

        IconButton(
            onClick = {
                text = ""
                onClear()
            },
            enabled = text.isNotEmpty()
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Clear",
                tint = iconTint
            )
        }
    }
}
