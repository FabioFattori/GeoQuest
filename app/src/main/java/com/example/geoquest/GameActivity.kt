package com.example.geoquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.geoquest.ui.components.layout.Helmet
import com.example.geoquest.ui.theme.GeoQuestTheme
import com.example.geoquest.utilities.Languages
import com.example.geoquest.utilities.LocaleHelper
import com.example.geoquest.utilities.PreferenceManager
import com.example.geoquest.utilities.navigation.GameNavigator

class GameActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val isDark = mutableStateOf(PreferenceManager.getTheme())
        val currentLang = mutableStateOf(PreferenceManager.getLanguage())
        setContent {
            val localizedContext =
                LocaleHelper.updateLocale(this, Languages.getLanguageFromCode(currentLang.value))
            CompositionLocalProvider(LocalContext provides localizedContext) {
                GeoQuestTheme(darkTheme = isDark.value) {
                    val navController = rememberNavController()

                    Helmet(
                        modifier = Modifier.fillMaxSize(),
                        navigator = navController,
                    ) {
                        GameNavigator(
                            navController = navController,
                            modifier = Modifier,
                            isDark = isDark,
                            currentLanguage = currentLang
                        )
                    }
                }

            }
        }
    }
}
