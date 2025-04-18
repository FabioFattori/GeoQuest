package com.example.geoquest.ui.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel

class ThemeViewModel(private val _isDark: MutableState<Boolean>) : ViewModel() {

    val isDark: State<Boolean> get() = _isDark

    fun toggleTheme() {
        _isDark.value = !_isDark.value
    }
}

