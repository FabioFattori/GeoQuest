package com.example.geoquest

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.example.geoquest.business.models.Player
import com.example.geoquest.ui.screens.BattleScreen
import com.example.geoquest.ui.theme.GeoQuestTheme
import com.example.geoquest.utilities.Languages
import com.example.geoquest.utilities.LocaleHelper
import com.example.geoquest.utilities.PreferenceManager

class BattleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferenceManager.init(this)
        val player = PreferenceManager.getObject("player", Player::class.java)
        val opponent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("opponent", Player::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Player>("opponent")
        }

        if (opponent == null || player == null) {
            throw Exception("opponent or player are null, player => ${player?.name}, opponent => ${opponent?.name}")
            finish()
            return
        }

        enableEdgeToEdge()
        setContent {
            val localizedContext =
                LocaleHelper.updateLocale(
                    this,
                    Languages.getLanguageFromCode(PreferenceManager.getLanguage())
                )

            CompositionLocalProvider(LocalContext provides localizedContext) {
                GeoQuestTheme(darkTheme = PreferenceManager.getTheme()) {
                    BattleScreen(
                        opponent = opponent,
                        player = player,
                    )
                }
            }
        }
    }
}
