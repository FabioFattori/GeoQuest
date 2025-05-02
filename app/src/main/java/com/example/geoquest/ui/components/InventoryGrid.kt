package com.example.geoquest.ui.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import com.example.geoquest.business.models.InventoryItem
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.geoquest.business.models.EquippableItem
import com.example.geoquest.business.models.UsableItem
import com.example.geoquest.ui.components.baseComponents.SingleItem
import com.example.geoquest.ui.components.baseComponents.SingleItemConfiguration
import com.example.geoquest.utilities.ImagesResolver

@Composable
fun InventoryGrid(items: List<InventoryItem>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = SingleItemConfiguration.size)
    ) {
        items(items) { item ->
            val color: Color
            var imageIndex = ""
            if (item is EquippableItem) {
                color = item.rarity.getColor()
                // TODO: aggiungi l'imageindex alla tabella db
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
                onClick = {}
            )
        }
    }

}
