package com.example.geoquest.ui.viewModels.factories

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.geoquest.ui.viewModels.ThemeViewModel

class ThemeViewModelFactory(private val isDark: MutableState<Boolean>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ThemeViewModel(isDark) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

