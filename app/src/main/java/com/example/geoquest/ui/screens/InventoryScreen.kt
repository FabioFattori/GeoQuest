package com.example.geoquest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.geoquest.business.models.Player
import com.example.geoquest.ui.components.baseComponents.styledDashedBorder
import com.example.geoquest.ui.theme.getGradient
import com.example.geoquest.utilities.PreferenceManager

@Composable
fun InventoryScreen(modifier: Modifier) {
    val player: Player? = PreferenceManager.getObject("player", Player::class.java)
    if (player == null) {
        Text(text = "Player not found", modifier = modifier)
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .styledDashedBorder(
                        brush = getGradient(),
                        strokeWidth = 6.dp,
                        dashLength = 12.dp,
                        gapLength = 18.dp,
                        cornerRadius = 16.dp
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("MYMAN", modifier = Modifier.padding(16.dp))
            }
        }
    }
}
