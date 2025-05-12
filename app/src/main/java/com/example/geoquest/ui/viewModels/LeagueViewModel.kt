package com.example.geoquest.ui.viewModels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geoquest.apiService.ApiService
import com.example.geoquest.business.models.EquippableItem
import com.example.geoquest.business.models.Player
import com.example.geoquest.business.models.enums.RewardResponse
import com.example.geoquest.utilities.PreferenceManager
import kotlinx.coroutines.launch
import com.example.geoquest.R
import com.example.geoquest.apiService.dto.requests.RewardRequest

class LeagueViewModel() : ViewModel() {
    val player = PreferenceManager.getObject("player", Player::class.java)!!

    // getLeague variables
    val isLeagueLoading = mutableStateOf(false)
    var playersInLeague = mutableListOf<Player>()
    val playerPosition = mutableIntStateOf(-1)

    // canGetReward variables
    val isAskingIfPlayerCanGetReward = mutableStateOf(false)
    val canGetRewardResult = mutableStateOf<RewardResponse?>(null)

    // getReward variables
    val isGettingReward = mutableStateOf(false)
    val reward = mutableStateOf<EquippableItem?>(null)

    // getOpponent variables
    val isAskingForOpponent = mutableStateOf(false)
    val opponent = mutableStateOf<Player?>(null)

    init {
        canPlayerGetReward()
    }

    fun getLeague() {
        viewModelScope.launch {
            isLeagueLoading.value = true
            try {
                val response = ApiService.retrofit.getCurrentLeague(player.id)
                if (response.isSuccessful) {
                    val body = response.body()!!
                    playersInLeague = body.players.toMutableList()
                    playerPosition.intValue = body.position
                    isLeagueLoading.value = false
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                isLeagueLoading.value = false
            }
        }
    }

    fun canPlayerGetReward() {
        viewModelScope.launch {
            isAskingIfPlayerCanGetReward.value = true
            val response = ApiService.retrofit.canPlayerGetReward(player.id)
            canGetRewardResult.value = RewardResponse.findEnumGivenStatusCode(response.code())
            isAskingIfPlayerCanGetReward.value = false
        }
    }

    fun getRarityColorOfReward(): Color {
        return when (playerPosition.intValue) {
            1 -> Color(0xFFFFD700)
            2, 3 -> Color(0xFFA335EE)
            in 4..20 -> Color(0xFF0070DD)
            else -> Color(0xFFA0A0A0)
        }
    }

    @Composable
    fun getRarityNameOfReward(): String {
        return stringResource(
            when (playerPosition.intValue) {
                1 -> R.string.legendary
                2, 3 -> R.string.epic
                in 4..20 -> R.string.rare
                else -> R.string.common
            }
        )
    }

    fun getReward() {
        if (canGetRewardResult.value != null && canGetRewardResult.value == RewardResponse.PlayerCanGetReward) {
            viewModelScope.launch {
                isGettingReward.value = true
                val response = ApiService.retrofit.getReward(
                    RewardRequest(player.id)
                )
                if (response.isSuccessful) {
                    reward.value = response.body()!!.reward
                }
                isGettingReward.value = false
            }
        }
    }

    fun getOpponent(onFinished : (player: Player) -> Unit) {
        viewModelScope.launch {
            isAskingForOpponent.value = true
            val response = ApiService.retrofit.findOpponent(player.id)
            if (response.isSuccessful) {
                opponent.value = response.body()!!
                onFinished(opponent.value!!)
            }
            isAskingForOpponent.value = false
        }
    }
}
