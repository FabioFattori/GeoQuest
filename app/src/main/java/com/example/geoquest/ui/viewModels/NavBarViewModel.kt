package com.example.geoquest.ui.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow



open class NavBarViewModel : ViewModel(){

        private val _shouldAnimate = MutableStateFlow(false)
        val shouldAnimate: StateFlow<Boolean> = _shouldAnimate

        fun triggerAnimation() {
            _shouldAnimate.value = true
        }

        fun resetAnimation() {
            _shouldAnimate.value = false
        }


}