package com.example.geoquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.geoquest.ui.components.Helmet
import com.example.geoquest.ui.theme.GeoQuestTheme
import com.example.geoquest.utilities.navigation.GameNavigator

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoQuestTheme {
                val navController = rememberNavController()

                Helmet(
                    modifier = Modifier.fillMaxSize(),
                    navigator = navController,
                ) {
                    GameNavigator(
                        navController = navController,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}
