package com.example.geoquest.business.models

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import java.util.Date

data class Rarity(
    val id: Int,
    val name: String,
    val hexColor: String,
    val multiplier: Int,
    val levelRequiredToDrop: Int,
    @SerializedName("created_at")
    val createdAt: Date,
    @SerializedName("updated_at")
    val updatedAt: Date,
) {
    fun getColor(): Color {
        val cleanedHex = this.hexColor.removePrefix("#")
        val colorLong = cleanedHex.toLong(16)
        return Color(colorLong or 0xFF000000)
    }
}
