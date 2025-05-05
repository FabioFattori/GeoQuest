package com.example.geoquest.business.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class UsableItem(
    override val id: Int,
    val name: String,
    val description: String,
    val rarityId: Int,
    val rarity: Rarity,
    val healthRecovery: Int,
    @SerializedName("imagePath")
    val imageIndex: String,
    val timeStamps: TimeStamps
) : InventoryItem

