package com.example.geoquest.business.models

data class Player(
    val id: Int,
    val name: String,
    val level: Int,
    val maxLevel: Int,
    val experienceCollected: Int,
    val nWonBattles: Int,
    val nBattles: Int,
    val currentHealth: Int,
    val experienceNeeded: Int,
    val experienceToLevelUp: Int,
    val damage: Int,
    val maxHealth: Int,
    val helmetId: Int?,
    val helmet: EquippableItem,
    val weaponId: Int?,
    val weapon: EquippableItem,
    val runeId: Int?,
    val rune: EquippableItem,
)
