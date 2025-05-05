package com.example.geoquest.ui.viewModels

import androidx.compose.runtime.mutableStateOf

class SingleItemController {
    internal val isClicked = mutableStateOf(false)

    fun reset() {
        isClicked.value = false
    }

    fun toggle() {
        isClicked.value = !isClicked.value
    }
}