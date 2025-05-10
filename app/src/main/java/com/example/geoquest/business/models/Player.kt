package com.example.geoquest.business.models

import com.example.geoquest.apiService.ApiService
import com.example.geoquest.apiService.dto.requests.UpdatePlayerRequest
import com.example.geoquest.business.models.enums.EquippableItemTypes
import com.example.geoquest.utilities.PreferenceManager

data class Player(
    val id: Int,
    val name: String,
    var level: Int,
    var maxLevel: Int,
    var experienceCollected: Int,
    val nWonBattles: Int,
    val nBattles: Int,
    var currentHealth: Int,
    var experienceNeeded: Int,
    val experienceToLevelUp: Int,
    var damage: Int,
    val maxHealth: Int,
    var helmetId: Int?,
    var helmet: EquippableItem,
    var weaponId: Int?,
    var weapon: EquippableItem,
    var runeId: Int?,
    var rune: EquippableItem,
    val collectedPoi: List<CollectedPoi>,
    val completedQuests : List<CompletedQuest>
){
    private suspend fun updateDbUser(){
            try {
                val response = ApiService.retrofit.updatePlayer(
                    playerId = id,
                    data = UpdatePlayerRequest(
                        name = name,
                        level = level,
                        experienceCollected = experienceCollected,
                        nWonBattles = nWonBattles,
                        nBattles = nBattles,
                        helmetId = helmetId,
                        weaponId = weaponId,
                        runeId = runeId,
                        currentHealth = currentHealth
                    )
                )
                if (response.isSuccessful) {
                    val res = response.body()
                    if (res != null) {
                        PreferenceManager.saveObject("player",res.player)
                    } else {
                        throw IllegalStateException("Equippable item is null")
                    }
                } else {
                    throw IllegalStateException("Response is not successful")
                }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        PreferenceManager.saveObject("player",this)
    }
    // return true if player leveled up, false otherwise
    suspend fun collectExp(collectedExp : Int): Boolean{
        experienceCollected += collectedExp
        experienceNeeded -= collectedExp
        val toReturn = experienceNeeded <= 0 || experienceToLevelUp <= 0
        if(toReturn) level++
        if(level > maxLevel) maxLevel = level
        updateDbUser()

        return toReturn
    }

    suspend fun equipItem(toEquip: EquippableItem){
        when(toEquip.blueprint.resolveType()){
            EquippableItemTypes.Weapon -> {
                weaponId = toEquip.id
                weapon = toEquip
            }
            EquippableItemTypes.Armor -> {
                helmetId = toEquip.id
                helmet = toEquip
            }
            EquippableItemTypes.Rune -> {
                runeId = toEquip.id
                rune = toEquip
            }
        }
        updateDbUser()
    }

    suspend fun useUsableitem(toUse: UsableItem){
        currentHealth += toUse.healthRecovery
        if(currentHealth > maxHealth) currentHealth = maxHealth
        // TODO: think if you want to add more features to the usableItems
        updateDbUser()
    }
}
