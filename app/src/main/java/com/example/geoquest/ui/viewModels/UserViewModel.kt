package com.example.geoquest.ui.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.geoquest.apiService.dto.responses.RegisterAndLoginResponse
import com.example.geoquest.utilities.PreferenceManager

class UserViewModel() : ViewModel() {
    val playerName = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val passwordConfirmation = mutableStateOf("")

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun checkLoginParams(): Pair<Boolean, String>? {
        if (email.value.isEmpty()) {
            return Pair(false, "L'email non può essere vuota")
        }

        if (password.value.isEmpty()) {
            return Pair(false, "La password non può essere vuota")
        }

        if (!isValidEmail(email.value.toString())) {
            return Pair(false, "L'email non è valida")
        }

        return null
    }

    fun checkRegisterData(): Pair<Boolean, String> {
        if (password.value != passwordConfirmation.value) {
            return Pair(false, "Le password non coincidono")
        }

        val validationResult = checkLoginParams()

        if (validationResult != null) {
            return validationResult
        }

        if (playerName.value.isEmpty()) {
            return Pair(false, "Il nome del personaggio non può essere vuoto")
        }

        return Pair(true, "Registrazione in corso...")
    }

    fun checkLoginData(): Pair<Boolean, String> {
        val validationResult = checkLoginParams()

        if (validationResult != null) {
            return validationResult
        }

        return Pair(true, "Accesso in corso...")
    }

    fun storeResponse(response: RegisterAndLoginResponse) {
        PreferenceManager.saveToken(response.token)
        PreferenceManager.saveObject("user", response.user)
        PreferenceManager.saveObject("player", response.player)
    }
}
