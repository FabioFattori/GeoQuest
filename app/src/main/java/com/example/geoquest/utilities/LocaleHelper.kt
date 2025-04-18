package com.example.geoquest.utilities

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

enum class Languages(val code: String) {
    ENGLISH("en"),
    ITALIAN("it");

    companion object {
        fun getLanguageFromCode(code: String): Languages {
            return when (code) {
                ENGLISH.code -> ENGLISH
                ITALIAN.code -> ITALIAN
                else -> ENGLISH // Default to English if the code is not recognized
            }
        }
    }
}

object LocaleHelper {
    fun updateLocale(context: Context, language: Languages): Context {
        val locale = Locale(language.code)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }
}
