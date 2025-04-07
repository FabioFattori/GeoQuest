package com.example.geoquest.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geoquest.apiService.ApiService
import com.example.geoquest.models.Player
import kotlinx.coroutines.launch

class PlayerViewModel : ViewModel() {
    private val _players = mutableStateListOf<Player>()
    val players: List<Player> = _players

    init {
        viewModelScope.launch {
            try {
                val response = ApiService.retrofit.getPlayers()
                _players.addAll(response)
            } catch (e: Exception) {
                Log.e("API", "Errore: ${e.message}")
            }
        }
    }
}