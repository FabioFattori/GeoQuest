package com.example.geoquest.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.SportsMma
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.geoquest.business.models.Player
import com.example.geoquest.ui.components.baseComponents.IconGradient
import com.example.geoquest.ui.components.baseComponents.styledDashedBorder
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getGradient
import com.example.geoquest.ui.theme.getSize

@Composable
fun PlayerStatsSimpleView(player: Player) {
    val iconSize = 67.dp
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .styledDashedBorder(
                brush = getGradient(),
                strokeWidth = 6.dp,
                dashLength = 12.dp,
                gapLength = 18.dp,
                cornerRadius = 16.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconGradient(
                    modifier = Modifier.size(iconSize),
                    contentDescription = "",
                    icon = Icons.Rounded.Favorite
                )
                Text(
                    "${player.currentHealth}/${player.maxHealth}",
                    fontSize = getSize(TextType.Title),
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconGradient(
                    modifier = Modifier.size(iconSize),
                    contentDescription = "",
                    icon = Icons.Rounded.SportsMma
                )
                Text(
                    "${player.damage}",
                    fontSize = getSize(TextType.Title),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}