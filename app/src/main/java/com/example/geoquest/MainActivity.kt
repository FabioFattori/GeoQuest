package com.example.geoquest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.geoquest.apiService.ApiService
import com.example.geoquest.apiService.dto.CheckTokenParams
import com.example.geoquest.business.models.User
import com.example.geoquest.utilities.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GeoQuest)
        setContentView(R.layout.activity_splash)

        PreferenceManager.init(this)

        val token = PreferenceManager.getToken()
        val user = PreferenceManager.getObject("user", User::class.java)

        lifecycleScope.launch(Dispatchers.IO) {
            delay(500) // necessario per permettere alla ui di non essere rallentata

            if (!token.isNullOrEmpty() && user != null) {
                try {
                    val response =
                        ApiService.retrofit.checkToken(CheckTokenParams(user.email, token))
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            goTo(GameActivity::class.java)
                        } else {
                            PreferenceManager.clearToken()
                            goTo(LoginActivity::class.java)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("SplashActivity", "Errore checkToken", e)
                    withContext(Dispatchers.Main) {
                        goTo(LoginActivity::class.java)
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    goTo(LoginActivity::class.java)
                }
            }
        }
    }

    private fun goTo(activity: Class<*>) {
        startActivity(Intent(this, activity))
        finish()
    }
}
