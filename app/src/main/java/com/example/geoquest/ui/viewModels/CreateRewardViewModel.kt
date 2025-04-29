package com.example.geoquest.ui.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geoquest.apiService.ApiService
import com.example.geoquest.apiService.dto.requests.CreateRandomItemRequest
import com.example.geoquest.business.models.Player
import com.example.geoquest.business.models.UsableItem
import com.example.geoquest.utilities.PreferenceManager
import com.mapbox.maps.PlaybackFinished
import kotlinx.coroutines.launch

enum class GenerateType {
    UsableItem,
    EquippableItem
}

class CreateRewardViewModel : ViewModel() {
    private val _isCreating = mutableStateOf(false)
    val isCreating: State<Boolean> get() = this._isCreating
    private val _generatedUsableItem = mutableStateOf<UsableItem?>(null)
    private val _generatedEquippableItem = mutableStateOf<UsableItem?>(null)
    val generatedUsableItem: State<UsableItem?> get() = _generatedUsableItem
    val generatedEquippableItem: State<UsableItem?> get() = _generatedEquippableItem

    fun clearGeneratedUsableItem() {
        _generatedUsableItem.value = null
    }

    fun clearGeneratedEquippableItem() {
        _generatedEquippableItem.value = null
    }

    fun createReward(typeToGenerate: GenerateType, onFinished: () -> Unit) {

        _isCreating.value = true
        val player = PreferenceManager.getObject("player", Player::class.java)
        if (player == null) {
            _isCreating.value = false
            throw IllegalStateException("Player is null")
        }
        viewModelScope.launch {
            try {
                val response = ApiService.retrofit.createRandomUsableItem(
                    CreateRandomItemRequest(
                        level = player.level,
                        ownerId = player.id
                    )
                )
                if (response.isSuccessful) {
                    val usableItem = response.body()
                    if (usableItem != null) {
                        _generatedUsableItem.value = usableItem.usableItem
                        _isCreating.value = false
                    } else {
                        throw IllegalStateException("Usable item is null")
                    }
                } else {
                    throw IllegalStateException("Response is not successful")
                }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            } finally {
                _isCreating.value = false
                onFinished()
            }
        }
    }
}
