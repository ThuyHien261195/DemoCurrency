package com.example.feature.currencyinfo.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mediator.currencyinfo.presentation.uimodel.CurrencyInfoUiModel

@Composable
fun CurrencyItemView(
    item: CurrencyInfoUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Circle avatar with first letter
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFF424242), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item.name.take(1).uppercase(),
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Name
        Text(
            text = item.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f) // push code + icon to end
        )

        // Code
        Text(
            text = item.symbol,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.width(6.dp))

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun PreviewCurrencyItemView() {
    CurrencyItemView(
        item = CurrencyInfoUiModel(
            id = "id",
            name = "Name",
            symbol = "SYM",
            code = "Code",
        )
    )
}
