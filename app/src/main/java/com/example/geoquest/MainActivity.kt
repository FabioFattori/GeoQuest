package com.example.geoquest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.geoquest.apiService.ApiService
import com.example.geoquest.apiService.dto.CheckTokenParams
import com.example.geoquest.business.models.User
import com.example.geoquest.ui.theme.GeoQuestTheme
import com.example.geoquest.utilities.PreferenceManager
import com.example.geoquest.utilities.navigation.LogRegisterNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init del preference manager
        PreferenceManager.init(this)

        val token = PreferenceManager.getToken()
        val user = PreferenceManager.getObject("user", User::class.java)

        if (!token.isNullOrEmpty() && user != null) {
            // Token e utente ci sono: verifica validità del token prima di aprire GameActivity
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = ApiService.retrofit.checkToken(
                        CheckTokenParams(email = user.email, token = token)
                    )

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            // Token valido: vai a GameActivity
                            val intent = Intent(this@MainActivity, GameActivity::class.java).apply {
                                flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }
                            startActivity(intent)
                            finish()
                        } else {
                            PreferenceManager.clearToken()
                            showLoginUI()
                        }
                    }

                } catch (e: Exception) {
                    Log.e("API", "Errore token: ${e.message}")
                    withContext(Dispatchers.Main) {
                        showLoginUI()
                    }
                }
            }
        } else {
            // Mancano token o user: mostra interfaccia login
            showLoginUI()
        }
    }

    private fun showLoginUI() {
        setContent {
            GeoQuestTheme {
                val navController = rememberNavController()

                LogRegisterNavigator(
                    navController = navController,
                    modifier = Modifier
                )
            }
        }
    }
}
