package com.example.geoquest.ui.components.baseComponents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.geoquest.business.models.EquippableItem
import com.example.geoquest.business.models.InventoryItem
import com.example.geoquest.business.models.Rarity
import com.example.geoquest.business.models.UsableItem
import com.example.geoquest.utilities.ImagesResolver

@Composable
fun SingleQuestItem(item: InventoryItem, modifier: Modifier) {
    var imageIndex = ""
    var rarity: Rarity? = null
    var title = remember { mutableStateOf("") }
    var desc = remember { mutableStateOf("") }
    if (item is EquippableItem) {
        imageIndex = item.blueprint.imagePath
        rarity = item.rarity
        title.value = item.blueprint.name
        desc.value = item.blueprint.description
    } else if (item is UsableItem) {
        imageIndex = item.imageIndex
        rarity = item.rarity
        title.value = item.name
        desc.value = item.description
    }

    RewardRow(
        modifier = modifier,
        image = {
            val images = ImagesResolver.associateDbImagesToPossibleImages()
            images[imageIndex]?.let { image ->
                ImagesResolver.GetImageComponent(image)
            }
        },
        rarityColor = rarity!!.getColor(),
        title = title.value,
        desc = desc.value
    )
}