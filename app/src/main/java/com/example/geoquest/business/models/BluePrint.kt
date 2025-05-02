package com.example.geoquest.business.models

import com.example.geoquest.business.models.enums.EquippableItemTypes
import com.google.gson.annotations.SerializedName
import java.util.Date

data class BluePrint(
    val id: Int,
    val name: String,
    val description: String,
    val baseDamage: Int,
    val baseHealth: Int,
    val imagePath: String,
    val requiredLevel: Int,
    val randomFactor: Int,
    val type: Int,
    @SerializedName("created_at")
    val createdAt: Date,
    @SerializedName("updated_at")
    val updatedAt: Date,
) {
    fun resolveType(): EquippableItemTypes {
        return when (type) {
            1 -> EquippableItemTypes.Weapon
            2 -> EquippableItemTypes.Armor
            3 -> EquippableItemTypes.Rune
            else -> throw Exception("ERRORE => il tipo dell'equippable item non può essere $type")
        }
    }
}
