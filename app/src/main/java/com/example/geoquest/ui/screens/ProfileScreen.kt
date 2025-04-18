package com.example.geoquest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.geoquest.R
import com.example.geoquest.business.models.Player
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getSize
import com.example.geoquest.utilities.PreferenceManager

@Composable
private fun DataPair(label: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.width(300.dp)
        ) {
            Text(label, fontSize = getSize(TextType.Normal), fontWeight = FontWeight.Bold)
        }

        Text(value, fontSize = getSize(TextType.Normal), fontWeight = FontWeight.Bold)

    }
}

@Composable
fun ProfileScreen(modifier: Modifier) {
    val player = PreferenceManager.getObject("player", Player::class.java)

    if (player == null) {
        Text("NO PLAYER", fontSize = getSize(TextType.BigTitle))
        return
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(player.name, fontSize = getSize(TextType.Title), fontWeight = FontWeight.Bold)

        DataPair(stringResource(R.string.level), player.level.toString())
        DataPair(stringResource(R.string.maxLevel), player.maxLevel.toString())
        DataPair(stringResource(R.string.damage), player.damage.toString())
        DataPair(stringResource(R.string.life), player.currentHealth.toString())
        DataPair(stringResource(R.string.maxLifa), player.maxHealth.toString())
        DataPair(stringResource(R.string.winBattles), player.nWonBattles.toString())
        DataPair(stringResource(R.string.totalBattles), player.nBattles.toString())
    }
}
