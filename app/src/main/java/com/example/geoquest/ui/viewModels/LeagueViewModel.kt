package com.example.geoquest.ui.viewModels

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geoquest.apiService.ApiService
import com.example.geoquest.business.models.Player
import com.example.geoquest.utilities.PreferenceManager
import kotlinx.coroutines.launch

class LeagueViewModel() : ViewModel() {
    val isLeagueLoading = mutableStateOf(false)
    var playersInLeague = mutableListOf<Player>()
    val playerPosition = mutableIntStateOf(-1)

    fun getLeague(){
        viewModelScope.launch {
            isLeagueLoading.value = true
            val player = PreferenceManager.getObject("player", Player::class.java)
            if(player == null) throw Exception("player is null")
            try {
                val response = ApiService.retrofit.getCurrentLeague(player.id)
                if(response.isSuccessful){
                    val body = response.body()!!
                    playersInLeague = body.players.toMutableList()
                    playerPosition.intValue = body.position
                    isLeagueLoading.value = false
                }
            }catch (ex : Exception){
                ex.printStackTrace()
                isLeagueLoading.value = false
            }
        }
    }
}