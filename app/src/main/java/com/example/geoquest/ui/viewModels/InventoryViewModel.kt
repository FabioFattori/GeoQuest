package com.example.geoquest.ui.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geoquest.apiService.ApiService
import com.example.geoquest.apiService.dto.requests.GetInventoryRequest
import com.example.geoquest.business.models.EquippableItem
import com.example.geoquest.business.models.Player
import com.example.geoquest.business.models.UsableItem
import com.example.geoquest.business.models.enums.EquippableItemTypes
import com.example.geoquest.utilities.PreferenceManager
import kotlinx.coroutines.launch

class InventoryViewModel : ViewModel() {
    private val _usableItems = mutableStateOf<List<UsableItem>>(emptyList())
    val usableItem: State<List<UsableItem>> = _usableItems
    val isLoadingUsableItems = mutableStateOf(false)

    private val _armors = mutableStateOf<List<EquippableItem>>(emptyList())
    val armors: State<List<EquippableItem>> = _armors
    val isLoadingArmors = mutableStateOf(false)

    private val _weapons = mutableStateOf<List<EquippableItem>>(emptyList())
    val weapons: State<List<EquippableItem>> = _weapons
    val isLoadingWeapons = mutableStateOf(false)

    private val _runes = mutableStateOf<List<EquippableItem>>(emptyList())
    val runes: State<List<EquippableItem>> = _runes
    val isLoadingRunes = mutableStateOf(false)

    fun getUsableItems(id: Int) {
        isLoadingUsableItems.value = true
        viewModelScope.launch {
            try {

                val response = ApiService.getTokenizedRequester(PreferenceManager.getToken()!!)
                    .getUsableItemInventory(id)
                if (response.isSuccessful) {
                    val data = response.body()
                    _usableItems.value = data ?: emptyList()
                    isLoadingUsableItems.value = false
                }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()

                isLoadingUsableItems.value = false
            }
        }
    }

    private fun toggleBasedOnType(type: EquippableItemTypes) {
        when (type) {
            EquippableItemTypes.Weapon -> isLoadingWeapons.value = !isLoadingWeapons.value
            EquippableItemTypes.Armor -> isLoadingArmors.value = !isLoadingArmors.value
            EquippableItemTypes.Rune -> isLoadingRunes.value = !isLoadingRunes.value
        }
    }

    fun getEquippableItems(id: Int, typeToGet: EquippableItemTypes) {
        toggleBasedOnType(typeToGet)
        viewModelScope.launch {
            try {
                val dataToSend = GetInventoryRequest.create(
                    id,
                    typeToGet
                )
                val response = ApiService.getTokenizedRequester(PreferenceManager.getToken()!!)
                    .getInventory(dataToSend.ownerId, dataToSend.type)
                if (response.isSuccessful) {
                    val data = response.body() ?: emptyList()

                    when (typeToGet) {
                        EquippableItemTypes.Weapon -> _weapons.value = data
                        EquippableItemTypes.Rune -> _runes.value = data
                        EquippableItemTypes.Armor -> _armors.value = data
                    }

                    toggleBasedOnType(typeToGet)
                }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()

                toggleBasedOnType(typeToGet)
            }
        }
    }

    fun getEquippableItemById(id: Int, onFinished: (EquippableItem?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = ApiService.retrofit
                    .getEquippableItemById(id)
                if (response.isSuccessful) {
                    onFinished(response.body())
                }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }

    fun getUsableItemById(id: Int, onFinished: (UsableItem?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = ApiService.retrofit.getUsableItemById(id)
                if (response.isSuccessful) {
                    onFinished(response.body())
                }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }

    fun removeUsableItem(toRem: UsableItem) {
        val currentList = _usableItems.value.toMutableList()
        currentList.remove(toRem)
        _usableItems.value = currentList
    }

    fun ownEquippableItem(toOwn : EquippableItem){
        val player = PreferenceManager.getObject("player", Player::class.java)
        if(player == null) throw Exception("NO PLAYER to be the owner")
        viewModelScope.launch {
            try {
                val response = ApiService.retrofit.ownEquippableItem(toOwn.id,player.id)
                if (!response.isSuccessful) {
                    throw Exception("ERROR IN THE REQUEST")
                }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }

    fun ownUsableItem(toOwn : UsableItem){
        val player = PreferenceManager.getObject("player", Player::class.java)
        if(player == null) throw Exception("NO PLAYER to be the owner")
        viewModelScope.launch {
            try {
                val response = ApiService.retrofit.ownUsableItem(toOwn.id,player.id)
                if (!response.isSuccessful) {
                    throw Exception("ERROR IN THE REQUEST")
                }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }

}