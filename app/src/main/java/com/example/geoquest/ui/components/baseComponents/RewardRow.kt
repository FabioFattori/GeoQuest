package com.example.geoquest.ui.components.baseComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getSize

@Composable
fun RewardRow(
    modifier: Modifier,
    image: @Composable () -> Unit,
    rarityColor: Color?,
    title: String,
    desc: String
) {
    val padding = 15.dp
    Row(
        modifier = modifier
            .height(SingleItemConfiguration.size + padding + padding)
            .fillMaxWidth()
            .padding(top = padding, bottom = padding),
        horizontalArrangement = Arrangement.Start,
    ) {
        SingleItem(
            modifier = Modifier,
            clickable = false,
            image = image,
            rarity = rarityColor
        )
        Column(
            modifier = Modifier.padding(start = 10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                title,
                fontWeight = FontWeight.Bold,
                fontSize = getSize(TextType.Normal)
            )
            Text(
                desc,
                fontWeight = FontWeight.Normal,
                fontSize = getSize(TextType.ButtonText)
            )
        }
    }
}