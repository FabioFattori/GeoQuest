package com.example.geoquest
//
//import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.geoquest.apiService.ApiService
import com.example.geoquest.apiService.dto.CheckTokenParams
import com.example.geoquest.business.models.User
import com.example.geoquest.ui.theme.GeoQuestTheme
import com.example.geoquest.utilities.PreferenceManager
import com.example.geoquest.utilities.navigation.AppNavHost


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoQuestTheme(darkTheme = true) {
                val context = LocalContext.current
                val navController = rememberNavController()

                // init preference manager
                PreferenceManager.init(context)

                LaunchedEffect(Unit) {
                    val token = PreferenceManager.getToken()
                    val user = PreferenceManager.getObject("user", User::class.java)

                    if (!token.isNullOrEmpty() && user != null) {
                        try {
                            val response = ApiService.retrofit.checkToken(
                                CheckTokenParams(email = user.email, token = token)
                            )

                            if (response.isSuccessful) {
                                context.startActivity(Intent(context, GameActivity::class.java))
                            } else {
                                PreferenceManager.clearToken()
                                navController.navigate("login")
                            }
                        } catch (e: Exception) {
                            Log.e("API", "Errore token: ${e.message}")
                            navController.navigate("login")
                        }
                    } else {
                        navController.navigate("login")
                    }

                }

                AppNavHost(
                    navController = navController,
                    modifier = Modifier
                )

            }
        }
    }
}

