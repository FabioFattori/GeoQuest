package com.example.geoquest.utilities

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.geoquest.business.classes.quests.Quest
import com.example.geoquest.business.classes.quests.QuestByExp
import com.example.geoquest.business.classes.quests.QuestByFoot
import com.google.gson.Gson
import org.json.JSONArray

object PreferenceManager {

    private const val PREF_NAME = "my_prefs"
    private lateinit var prefs: SharedPreferences
    private val gson = Gson()

    fun isInitialized(): Boolean {
        return ::prefs.isInitialized
    }

    fun init(context: Context) {
        if (!isInitialized()) {
            prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
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

    fun saveQuests(lst: List<Quest>) {
        val jsonArray = JSONArray()
        for (quest in lst) {
            jsonArray.put(quest.toJson())
        }
        prefs.edit { putString("quests", jsonArray.toString()) }
    }

    fun getQuests(context: Context, onFinished: (List<Quest>) -> Unit) {
        val storedQuests = prefs.getString("quests", "") ?: ""
        if (storedQuests.isEmpty()) {
            return onFinished(emptyList())
        }
        var loadedQuests = 0

        val jsonArray = JSONArray(storedQuests)
        val questList = mutableListOf<Quest>()

        for (i in 0..jsonArray.length() - 1) {
            val jsonObject = jsonArray.getJSONObject(i)
            val type = jsonObject.getString("type")
            when (type) {
                "ExpQuest" -> QuestByExp.fromJson(jsonObject, context) { quest ->
                    questList.add(quest)
                    loadedQuests++
                    if (loadedQuests == jsonArray.length())
                        onFinished(questList)
                }

                "FootQuest" -> QuestByFoot.fromJson(jsonObject, context) { quest ->
                    questList.add(quest)
                    loadedQuests++
                    if (loadedQuests == jsonArray.length())
                        onFinished(questList)
                }

                else -> onFinished(questList)
            }
        }
        if (loadedQuests == jsonArray.length()) {
            onFinished(questList)
        }
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
        clearObject("quests")
    }
}
