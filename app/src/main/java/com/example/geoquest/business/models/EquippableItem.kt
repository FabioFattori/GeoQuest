package com.example.geoquest.business.models


data class EquippableItem(
    override val id: Int,
    val blueprintId: Int,
    val rarityId: Int,
    val ownerId: Int?,
    val damage: Int,
    val health: Int,
    val blueprint: BluePrint,
    val rarity: Rarity,
) : InventoryItem{
    fun getActualDamage() : Int{
        return damage + blueprint.baseDamage
    }

    fun getActualHealth() : Int{
        return health + blueprint.baseHealth
    }
}
