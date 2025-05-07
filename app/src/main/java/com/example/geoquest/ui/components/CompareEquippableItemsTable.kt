package com.example.geoquest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.geoquest.business.models.EquippableItem
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getGradient
import com.example.geoquest.ui.theme.getSize


private fun getDisplayString(value: Int): String {
    return when {
        value > 0 -> "+$value"
        value < 0 -> value.toString()
        else -> "0"
    }
}

@Composable
fun StatTable(
    toCompare: EquippableItem,
    playerItem: EquippableItem?,
    damageString: String,
    healthString: String
) {
    val weight = 1f

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .padding(bottom = 10.dp)
    ) {
        // Intestazione
        // TODO: Change those string to be localized
        Row(modifier = Modifier.fillMaxWidth()) {
            TableCell("", modifier = Modifier.weight(weight))
            TableCell("oggetto", modifier = Modifier.weight(weight))
            TableCell("corrente", modifier = Modifier.weight(weight))
        }
        // Riga Vita
        Row(modifier = Modifier.fillMaxWidth()) {
            TableCell(healthString, modifier = Modifier.weight(weight))
            TableCell(
                getDisplayString(toCompare.getActualHealth()),
                modifier = Modifier.weight(weight)
            )
            TableCell(
                getDisplayString(playerItem?.getActualHealth() ?: 0),
                modifier = Modifier.weight(weight)
            )
        }
        // Riga Danno
        Row(modifier = Modifier.fillMaxWidth()) {
            TableCell(damageString, modifier = Modifier.weight(weight))
            TableCell(
                getDisplayString(toCompare.getActualDamage()),
                modifier = Modifier.weight(weight)
            )
            TableCell(
                getDisplayString(playerItem?.getActualDamage() ?: 0),
                modifier = Modifier.weight(weight)
            )
        }
    }
}

@Composable
fun TableCell(
    text: String, modifier: Modifier
) {
    Box(
        modifier = if (text.isEmpty()) modifier.padding(8.dp) else modifier
            .border(1.dp, getGradient(), shape = RectangleShape)
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Normal,
            fontSize = getSize(TextType.SmallText)
        )
    }
}
