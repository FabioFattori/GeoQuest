package com.example.geoquest.ui.viewModels

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel

class ThemeViewModel(private val _isDark: MutableState<Boolean>) : ViewModel() {

    fun toggleTheme() {
        _isDark.value = !_isDark.value
    }
}

