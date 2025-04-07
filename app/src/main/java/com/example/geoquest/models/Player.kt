package com.example.geoquest.models


data class Player(
    val name: String,
    val level: Int,
    val experienceCollected: Int,
    val nWonBattles: Int,
    val nBattles: Int,
    val currentHealth: Int,
    val experienceNeeded: Int,
    val damage: Int,
    val maxHealth:Int,
    val helmet: EquippableItem,
    val weapon: EquippableItem,
    val rune: EquippableItem
) {
}