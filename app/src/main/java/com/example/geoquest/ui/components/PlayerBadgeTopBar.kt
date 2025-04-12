package com.example.geoquest.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.geoquest.business.models.Player
import com.example.geoquest.utilities.PreferenceManager

@Composable
fun PlayerBadgeForTopBar(modifier: Modifier) {
    // get the user, if it is null throw error
    val player = try {
        PreferenceManager.getObject("player", Player::class.java)
    } catch (ex: Exception) {
        Log.d("ERROR", "No player found, you're not supposed to be here! ${ex.message}")
        null
    }

    if (player == null) {
        Text("No player")
        return
    }

    val percMissingExp = player.experienceCollected * 100 / player.experienceNeeded

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = player.name)
        Text(text = "$percMissingExp%")
    }
}
