package com.example.geoquest.business.models

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.geoquest.business.models.enums.EquippableItemTypes
import com.example.geoquest.R
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
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
    val timeStamps:@RawValue TimeStamps
) : Parcelable {
    fun resolveType(): EquippableItemTypes {
        return when (type) {
            1 -> EquippableItemTypes.Weapon
            2 -> EquippableItemTypes.Armor
            3 -> EquippableItemTypes.Rune
            else -> throw Exception("ERRORE => il tipo dell'equippable item non può essere $type")
        }
    }

    @Composable
    fun getRuneString(): String {
        return stringResource(
            when (resolveType()) {
                EquippableItemTypes.Rune -> R.string.rune
                EquippableItemTypes.Weapon -> R.string.weapon
                EquippableItemTypes.Armor -> R.string.armor
            }
        )
    }
}
