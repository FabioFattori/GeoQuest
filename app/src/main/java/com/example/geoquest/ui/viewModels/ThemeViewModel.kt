package com.example.geoquest.ui.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.geoquest.utilities.PreferenceManager

class ThemeViewModel : ViewModel() {
    private val _isDark = mutableStateOf(PreferenceManager.getTheme())
    val isDark: State<Boolean> get() = _isDark

    fun toggleTheme() {
        _isDark.value = !_isDark.value
    }
}

