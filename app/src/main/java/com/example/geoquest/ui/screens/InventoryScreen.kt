package com.example.geoquest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.geoquest.business.models.EquippableItem
import com.example.geoquest.business.models.Player
import com.example.geoquest.business.models.enums.EquippableItemTypes
import com.example.geoquest.ui.components.InventoryGrid
import com.example.geoquest.ui.components.baseComponents.BigLoader
import com.example.geoquest.ui.components.baseComponents.PlayerStatsSimpleView
import com.example.geoquest.ui.components.baseComponents.SingleItem
import com.example.geoquest.ui.components.baseComponents.SingleItemConfiguration
import com.example.geoquest.ui.viewModels.SingleItemController
import com.example.geoquest.ui.viewModels.factories.GlobalViewModels
import com.example.geoquest.utilities.ImagesResolver
import com.example.geoquest.utilities.PreferenceManager

enum class Modes {
    UsableItems,
    Armor,
    Runes,
    Weapons
}

fun getColorOrNull(item: EquippableItem?): Color? {
    if (item == null) return null
    return item.rarity.getColor()
}

@Composable
fun GetItemImage(item: EquippableItem?) {
    if (item == null) return
    val image = ImagesResolver.associateDbImagesToPossibleImages()[item.blueprint.imagePath]
    return ImagesResolver.GetImageComponent(image!!)
}

@Composable
fun InventoryScreen(modifier: Modifier) {
    val player: Player? = PreferenceManager.getObject("player", Player::class.java)
    val dataGetter = GlobalViewModels.inventoryHandler
    val mode = remember { mutableStateOf(Modes.UsableItems) }
    val equipmentController = remember {
        mapOf(
            "weapon" to SingleItemController(),
            "helmet" to SingleItemController(),
            "rune" to SingleItemController()
        )
    }

    val needToReload = GlobalViewModels.inventoryReloader.shouldAnimate.collectAsState()



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
            if (needToReload.value) {
                BigLoader()
                LaunchedEffect(Unit) {

                    // Reset dopo l'animazione se serve
                    GlobalViewModels.inventoryReloader.resetAnimation()
                }
            } else {
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
                        image = { GetItemImage(player.weapon) },
                        clickable = true,
                        onClick = {
                            if (mode.value == Modes.Weapons) {
                                mode.value = Modes.UsableItems
                            } else {
                                mode.value = Modes.Weapons
                            }
                            requestAlreadyMade.value = false
                            equipmentController["helmet"]?.reset()
                            equipmentController["rune"]?.reset()
                        },
                        controller = equipmentController["weapon"]!!,
                        rarity = getColorOrNull(player.weapon)
                    )
                    SingleItem(
                        modifier = modifier,
                        image = { GetItemImage(player.helmet) },
                        clickable = true,
                        onClick = {
                            if (mode.value == Modes.Armor) {
                                mode.value = Modes.UsableItems
                            } else {
                                mode.value = Modes.Armor
                            }
                            requestAlreadyMade.value = false
                            equipmentController["weapon"]?.reset()
                            equipmentController["rune"]?.reset()
                        },
                        controller = equipmentController["helmet"]!!,
                        rarity = getColorOrNull(player.helmet)
                    )
                    SingleItem(
                        modifier = modifier,
                        image = { GetItemImage(player.rune) },
                        clickable = true,
                        onClick = {
                            if (mode.value == Modes.Runes) {
                                mode.value = Modes.UsableItems
                            } else {
                                mode.value = Modes.Runes
                            }
                            requestAlreadyMade.value = false
                            equipmentController["helmet"]?.reset()
                            equipmentController["weapon"]?.reset()
                        },
                        controller = equipmentController["rune"]!!,
                        rarity = getColorOrNull(player.rune)
                    )
                }
                if (isLoading) {
                    BigLoader()
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 15.dp)
                    ) {
                        InventoryGrid(
                            items = when (mode.value) {
                                Modes.UsableItems -> dataGetter.usableItem.value
                                Modes.Weapons -> dataGetter.weapons.value
                                Modes.Armor -> dataGetter.armors.value
                                Modes.Runes -> dataGetter.runes.value
                            },
                            player = player,
                            mode = mode.value
                        )
                    }

                }
            }
        }
    }
}
