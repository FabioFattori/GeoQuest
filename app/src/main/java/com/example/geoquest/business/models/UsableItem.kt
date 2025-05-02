package com.example.geoquest.business.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class UsableItem(
    val id: Int,
    val name: String,
    val description: String,
    val rarityId: Int,
    val rarity: Rarity,
    val healthRecovery: Int,
    @SerializedName("imagePath")
    val imageIndex: String,
    @SerializedName("created_at")
    val createdAt: Date,
    @SerializedName("updated_at")
    val updatedAt: Date,
) : InventoryItem

