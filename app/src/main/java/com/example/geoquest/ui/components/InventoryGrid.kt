package com.example.geoquest.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import com.example.geoquest.business.models.InventoryItem
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.geoquest.business.models.EquippableItem
import com.example.geoquest.business.models.Player
import com.example.geoquest.business.models.UsableItem
import com.example.geoquest.ui.components.baseComponents.SingleItem
import com.example.geoquest.ui.components.baseComponents.SingleItemConfiguration
import com.example.geoquest.ui.components.dialogs.EquippableItemDialog
import com.example.geoquest.ui.screens.Modes
import com.example.geoquest.ui.viewModels.InventoryEquipperViewModel
import com.example.geoquest.ui.viewModels.SingleItemController
import com.example.geoquest.ui.viewModels.factories.GlobalViewModels
import com.example.geoquest.utilities.ImagesResolver

@Composable
fun InventoryGrid(items: List<InventoryItem>, player: Player, mode: Modes) {
    val clickedElement = remember { mutableStateOf<InventoryItem?>(null) }
    val isEquippableItemDialogOpen = remember { mutableStateOf(false) }
    val isUsableItemDialogOpen = remember { mutableStateOf(false) }

    val controllers = remember {
        items.associate { it.id to SingleItemController() }
    }

    val currentClickedController = remember {
        mutableStateOf<SingleItemController?>(null)
    }

    LazyVerticalGrid(
        modifier = Modifier.padding(top = 28.dp),
        columns = GridCells.FixedSize(SingleItemConfiguration.size),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(items) { item ->
            val color: Color
            var imageIndex = ""
            val controller = controllers[item.id]!!

            if (item is EquippableItem) {
                color = item.rarity.getColor()
                imageIndex = item.blueprint.imagePath
            } else {
                val usableItem = item as UsableItem
                color = usableItem.rarity.getColor()
                imageIndex = usableItem.imageIndex
            }
            SingleItem(
                modifier = Modifier,
                rarity = color,
                image = {
                    val res = ImagesResolver.associateDbImagesToPossibleImages()[imageIndex]
                    if (res == null) {
                        Unit
                    } else {
                        ImagesResolver.GetImageComponent(res)
                    }
                },
                clickable = true,
                controller = controller,
                onClick = {
                    if (currentClickedController.value != null) {
                        currentClickedController.value!!.reset()
                    }
                    currentClickedController.value = controller
                    clickedElement.value = item
                    if (item is EquippableItem) {
                        isEquippableItemDialogOpen.value = true
                    } else if (item is UsableItem) {
                        isUsableItemDialogOpen.value = true
                    }
                }
            )
        }
    }

    when {
        isEquippableItemDialogOpen.value -> {
            EquippableItemDialog(
                onDismissRequest = {
                    isEquippableItemDialogOpen.value = false
                    currentClickedController.value!!.reset()
                },
                onConfirmation = {
                    isEquippableItemDialogOpen.value = false
                    currentClickedController.value!!.reset()
                    InventoryEquipperViewModel().equipItem(
                        clickedElement.value as EquippableItem,
                        player
                    )
                    GlobalViewModels.inventoryReloader.triggerAnimation()
                },
                toShow = clickedElement.value as EquippableItem,
                playerItem = when (mode) {
                    Modes.Weapons -> player.weapon
                    Modes.Runes -> player.rune
                    Modes.Armor -> player.helmet
                    else -> throw Exception("NOT SUPPOSED TO BE HERE WITH THIS TYPE")
                }
            )
        }

        isUsableItemDialogOpen.value -> {

        }
    }
}
