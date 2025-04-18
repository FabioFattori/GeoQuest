package com.example.geoquest.ui.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.geoquest.utilities.PreferenceManager

class LanguageViewModel : ViewModel() {
    private val _currentLang = mutableStateOf(PreferenceManager.getLanguage())
    val currentLang: String
        get() = _currentLang.value!!

    fun toggleLanguage() {
        _currentLang.value = if (_currentLang.value == "it") "en" else "it"
    }
}
