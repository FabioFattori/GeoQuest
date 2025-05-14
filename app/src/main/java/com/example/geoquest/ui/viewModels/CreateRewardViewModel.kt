package com.example.geoquest.ui.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geoquest.apiService.ApiService
import com.example.geoquest.apiService.dto.requests.CreateRandomItemRequest
import com.example.geoquest.business.models.EquippableItem
import com.example.geoquest.business.models.Player
import com.example.geoquest.business.models.UsableItem
import com.example.geoquest.ui.viewModels.factories.GlobalViewModels
import com.example.geoquest.utilities.PreferenceManager
import kotlinx.coroutines.launch

enum class GenerateType {
    UsableItem,
    EquippableItem
}

class CreateRewardViewModel : ViewModel() {
    private val _isCreating = mutableStateOf(false)
    val isCreating: State<Boolean> get() = this._isCreating
    private val _generatedUsableItem = mutableStateOf<UsableItem?>(null)
    private val _generatedEquippableItem = mutableStateOf<EquippableItem?>(null)
    val generatedUsableItem: State<UsableItem?> get() = _generatedUsableItem
    val generatedEquippableItem: State<EquippableItem?> get() = _generatedEquippableItem

    fun clearGeneratedUsableItem() {
        _generatedUsableItem.value = null
    }

    fun clearGeneratedEquippableItem() {
        _generatedEquippableItem.value = null
    }

    private fun createUsable(
        player: Player,
        onFinished: () -> Unit,
        giveItemToPlayer: Boolean = true
    ) {
        viewModelScope.launch {
            try {
                val response = ApiService.retrofit.createRandomUsableItem(
                    CreateRandomItemRequest(
                        level = player.level,
                        ownerId = if (giveItemToPlayer) player.id else null
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

    private fun createEquippable(
        player: Player,
        onFinished: () -> Unit,
        giveItemToPlayer: Boolean = true
    ) {
        viewModelScope.launch {
            try {
                val response = ApiService.retrofit.createRandomEquippableItem(
                    CreateRandomItemRequest(
                        level = player.level,
                        ownerId = if (giveItemToPlayer) player.id else null
                    )
                )
                if (response.isSuccessful) {
                    val equippableItem = response.body()
                    if (equippableItem != null) {
                        _generatedEquippableItem.value = equippableItem
                        _isCreating.value = false
                    } else {
                        throw IllegalStateException("Equippable item is null")
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

    fun createReward(
        typeToGenerate: GenerateType,
        onFinished: () -> Unit,
        giveItemToPlayer: Boolean = true
    ) {

        _isCreating.value = true
        val player = PreferenceManager.getObject("player", Player::class.java)
        if (player == null) {
            _isCreating.value = false
            throw IllegalStateException("Player is null")
        }
        when (typeToGenerate) {
            GenerateType.UsableItem -> createUsable(player, onFinished, giveItemToPlayer)
            GenerateType.EquippableItem -> createEquippable(player, onFinished, giveItemToPlayer)
        }

        if (giveItemToPlayer) {
            viewModelScope.launch {
                try {
                    player.collectExp(200)
                    GlobalViewModels.navBarViewModel.triggerAnimation()
                } catch (e: Exception) {
                    // Handle error
                    e.printStackTrace()
                }
            }
        }
    }
}
