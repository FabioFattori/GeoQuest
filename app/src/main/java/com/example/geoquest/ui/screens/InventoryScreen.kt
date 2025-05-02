package com.example.geoquest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.geoquest.business.models.Player
import com.example.geoquest.business.models.enums.EquippableItemTypes
import com.example.geoquest.ui.components.InventoryGrid
import com.example.geoquest.ui.components.PlayerStatsSimpleView
import com.example.geoquest.ui.components.baseComponents.SingleItem
import com.example.geoquest.ui.components.baseComponents.SingleItemConfiguration
import com.example.geoquest.ui.viewModels.InventoryViewModel
import com.example.geoquest.utilities.PreferenceManager

private enum class Modes {
    UsableItems,
    Armor,
    Runes,
    Weapons
}

@Composable
fun InventoryScreen(modifier: Modifier) {
    val player: Player? = PreferenceManager.getObject("player", Player::class.java)
    val dataGetter = remember { InventoryViewModel() }
    val mode = remember { mutableStateOf(Modes.UsableItems) }


    if (player == null) {
        Text(text = "Player not found", modifier = modifier)
    } else {
        val requestAlreadyMade = remember { mutableStateOf(false) }

        when (mode.value) {
            Modes.UsableItems -> {
                if (!requestAlreadyMade.value && !dataGetter.isLoadingUsableItems.value) {
                    dataGetter.getUsableItems(player.id)
                    requestAlreadyMade.value = true
                }
            }

            Modes.Weapons -> {
                if (!requestAlreadyMade.value && !dataGetter.isLoadingWeapons.value) {
                    dataGetter.getEquippableItems(player.id, EquippableItemTypes.Weapon)
                    requestAlreadyMade.value = true
                }
            }

            Modes.Armor -> {
                if (!requestAlreadyMade.value && !dataGetter.isLoadingArmors.value) {
                    dataGetter.getEquippableItems(player.id, EquippableItemTypes.Armor)
                    requestAlreadyMade.value = true
                }
            }

            Modes.Runes -> {
                if (!requestAlreadyMade.value && !dataGetter.isLoadingRunes.value) {
                    dataGetter.getEquippableItems(player.id, EquippableItemTypes.Rune)
                    requestAlreadyMade.value = true
                }
            }
        }

        // Loading reactive senza variabili locali
        val isLoading = when (mode.value) {
            Modes.UsableItems -> dataGetter.isLoadingUsableItems.value
            Modes.Weapons -> dataGetter.isLoadingWeapons.value
            Modes.Armor -> dataGetter.isLoadingArmors.value
            Modes.Runes -> dataGetter.isLoadingRunes.value
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PlayerStatsSimpleView(
                player = player,
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .height(SingleItemConfiguration.size + 20.dp)
                    .padding(top = 20.dp)
                    .fillMaxWidth()
            ) {
                SingleItem(
                    modifier = modifier,
                    image = {},
                    clickable = true,
                    onClick = {
                        if(mode.value == Modes.Weapons){
                            mode.value = Modes.UsableItems
                        }else{
                            mode.value = Modes.Weapons
                        }
                        requestAlreadyMade.value = false
                    },
                    rarity = null
                )
                SingleItem(
                    modifier = modifier,
                    image = {},
                    clickable = true,
                    onClick = {
                        if(mode.value == Modes.Armor){
                            mode.value = Modes.UsableItems
                        }else{
                            mode.value = Modes.Armor
                        }
                        requestAlreadyMade.value = false
                    },
                    rarity = null
                )
                SingleItem(
                    modifier = modifier,
                    image = {},
                    clickable = true,
                    onClick = {
                        if(mode.value == Modes.Runes){
                            mode.value = Modes.UsableItems
                        }else{
                            mode.value = Modes.Runes
                        }
                        requestAlreadyMade.value = false
                    },
                    rarity = null
                )
            }
            if (isLoading) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(150.dp)
                    )
                }
            } else {
                InventoryGrid(
                    items = when (mode.value) {
                        Modes.UsableItems -> dataGetter.usableItem.value
                        Modes.Weapons -> dataGetter.weapons.value
                        Modes.Armor -> dataGetter.armors.value
                        Modes.Runes -> dataGetter.runes.value
                    }
                )
            }
        }
    }
}
