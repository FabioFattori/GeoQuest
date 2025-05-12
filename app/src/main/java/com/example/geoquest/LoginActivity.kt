package com.example.geoquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.geoquest.ui.theme.GeoQuestTheme
import com.example.geoquest.utilities.Languages
import com.example.geoquest.utilities.LocaleHelper
import com.example.geoquest.utilities.PreferenceManager
import com.example.geoquest.utilities.navigation.LogRegisterNavigator


class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PreferenceManager.init(this)
        val theme = PreferenceManager.getTheme()

        val localizedContext = LocaleHelper.updateLocale(
            this, Languages.getLanguageFromCode(PreferenceManager.getLanguage())
        )

        setContent {
            CompositionLocalProvider(LocalContext provides localizedContext) {
                GeoQuestTheme(darkTheme = theme) {
                    val navController = rememberNavController()

                    LogRegisterNavigator(
                        navController = navController, modifier = Modifier
                    )
                }
            }
        }
    }
}

