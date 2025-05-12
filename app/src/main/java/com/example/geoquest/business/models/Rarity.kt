package com.example.geoquest.business.models

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Rarity(
    val id: Int,
    val name: String,
    val hexColor: String,
    val multiplier: Int,
    val levelRequiredToDrop: Int,
    val timeStamps:@RawValue TimeStamps
) : Parcelable {
    fun getColor(): Color {
        val cleanedHex = this.hexColor.removePrefix("#")
        val colorLong = cleanedHex.toLong(16)
        return Color(colorLong or 0xFF000000)
    }
}
