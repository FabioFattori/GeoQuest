package com.example.geoquest.utilities

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson

object PreferenceManager {

    private const val PREF_NAME = "my_prefs"
    private lateinit var prefs: SharedPreferences
    private val gson = Gson()

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        prefs.edit { putString("auth_token", token) }
    }

    fun getToken(): String? {
        return prefs.getString("auth_token", null)
    }

    fun clearToken() {
        prefs.edit { remove("auth_token") }
    }

    fun saveTheme(isDarkTheme: Boolean) {
        prefs.edit { putBoolean("isDarkTheme", isDarkTheme) }
    }

    fun getTheme(): Boolean {
        return prefs.getBoolean("isDarkTheme", false)
    }

    fun saveLanguage(language: Languages) {
        prefs.edit { putString("language", language.code) }
    }

    fun getLanguage(): String {
        return prefs.getString("language", Languages.ITALIAN.code)!!
    }

    fun <T> saveObject(key: String, obj: T) {
        val json = gson.toJson(obj)
        prefs.edit { putString(key, json) }
    }

    fun <T> getObject(key: String, clazz: Class<T>): T? {
        val json = prefs.getString(key, null) ?: return null
        return gson.fromJson(json, clazz)
    }

    fun clearObject(key: String) {
        prefs.edit { remove(key) }
    }

    fun clearAll() {
        this.clearToken()
        clearObject("user")
        clearObject("player")
        clearObject("isDarkTheme")
        clearObject("language")
    }
}
