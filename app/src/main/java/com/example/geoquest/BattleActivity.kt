package com.example.geoquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.geoquest.ui.components.layout.Helmet
import com.example.geoquest.ui.theme.GeoQuestTheme
import com.example.geoquest.utilities.Languages
import com.example.geoquest.utilities.LocaleHelper
import com.example.geoquest.utilities.PreferenceManager
import com.example.geoquest.utilities.navigation.GameNavigator
import com.example.geoquest.utilities.navigation.LogRegisterNavigator

class BattleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val localizedContext =
                LocaleHelper.updateLocale(this, Languages.getLanguageFromCode(PreferenceManager.getLanguage()))

            CompositionLocalProvider(LocalContext provides localizedContext) {
                GeoQuestTheme(darkTheme = PreferenceManager.getTheme()) {

                }
            }
        }
    }
}
