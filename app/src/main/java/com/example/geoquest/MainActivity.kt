package com.example.geoquest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.geoquest.apiService.ApiService
import com.example.geoquest.apiService.dto.CheckTokenParams
import com.example.geoquest.business.models.User
import com.example.geoquest.ui.components.Logo
import com.example.geoquest.ui.theme.GeoQuestTheme
import com.example.geoquest.utilities.PreferenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        PreferenceManager.init(this)

        val isDark = PreferenceManager.getTheme()
        val token = PreferenceManager.getToken()
        val user = PreferenceManager.getObject("user", User::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val isValid = checkTokenValidity(token, user)

            withContext(Dispatchers.Main) {
                val nextActivity = if (isValid) {
                    GameActivity::class.java
                } else {
                    PreferenceManager.clearAll()
                    LoginActivity::class.java
                }

                startActivity(Intent(this@MainActivity, nextActivity))
                finish()
            }
        }

        setContent {
            GeoQuestTheme(darkTheme = isDark) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                ) {
                    Logo()
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(200.dp)
                            .padding(top = 100.dp),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }

    private suspend fun checkTokenValidity(token: String?, user: User?): Boolean {
        if (token.isNullOrEmpty() || user == null) return false

        return try {
            val response = ApiService.retrofit.checkToken(CheckTokenParams(user.email, token))
            response.isSuccessful
        } catch (e: Exception) {
            Log.d("403", "Not authenticated, token not valid")
            false
        }
    }
}
