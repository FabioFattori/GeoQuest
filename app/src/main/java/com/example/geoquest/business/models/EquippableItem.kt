package com.example.geoquest.business.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class EquippableItem(
    override val id: Int,
    val blueprintId: Int,
    val rarityId: Int,
    val ownerId: Int?,
    val damage: Int,
    val health: Int,
    val blueprint:@RawValue BluePrint,
    val rarity:@RawValue Rarity,
) : InventoryItem, Parcelable {
    fun getActualDamage() : Int{
        return damage + blueprint.baseDamage * rarity.multiplier
    }

    fun getActualHealth() : Int{
        return health + blueprint.baseHealth * rarity.multiplier
    }
}
