package com.example.geoquest.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geoquest.business.models.EquippableItem
import com.example.geoquest.business.models.Player
import kotlinx.coroutines.launch

class InventoryEquipperViewModel : ViewModel() {
    fun equipItem(toEquip: EquippableItem,player: Player){
        viewModelScope.launch { 
            player.equipItem(toEquip)
        }
    }
}