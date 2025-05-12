package com.example.geoquest.business.models

import android.os.Parcelable
import com.example.geoquest.apiService.ApiService
import com.example.geoquest.apiService.dto.requests.UpdatePlayerRequest
import com.example.geoquest.business.models.enums.EquippableItemTypes
import com.example.geoquest.ui.viewModels.factories.GlobalViewModels
import com.example.geoquest.utilities.PreferenceManager
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Player(
    val id: Int,
    val name: String,
    var level: Int,
    var maxLevel: Int,
    var experienceCollected: Int,
    var nWonBattles: Int,
    var nBattles: Int,
    var currentHealth: Int,
    var experienceNeeded: Int,
    val experienceToLevelUp: Int,
    var damage: Int,
    val maxHealth: Int,
    var helmetId: Int?,
    var helmet: @RawValue EquippableItem?,
    var weaponId: Int?,
    var weapon: @RawValue EquippableItem?,
    var runeId: Int?,
    var rune: @RawValue EquippableItem?,
    val collectedPoi: @RawValue List<CollectedPoi>? = emptyList(),
    val completedQuests: @RawValue List<CompletedQuest>? = emptyList(),
) : Parcelable {
    private suspend fun updateDbUser() {
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
                    currentHealth = currentHealth,
                    maxHealth = maxHealth,
                    maxLevel = maxLevel
                )
            )
            if (response.isSuccessful) {
                val res = response.body()
                if (res != null) {
                    PreferenceManager.saveObject("player", res.player)
                    GlobalViewModels.navBarViewModel.triggerAnimation()
                    GlobalViewModels.inventoryReloader.triggerAnimation()
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
    }

    // return true if player leveled up, false otherwise
    suspend fun collectExp(collectedExp: Int): Boolean {
        experienceCollected += collectedExp
        experienceNeeded -= collectedExp
        val toReturn = experienceNeeded <= 0 || experienceToLevelUp <= 0
        if (toReturn) level++
        if (level > maxLevel) maxLevel = level
        updateDbUser()

        return toReturn
    }

    suspend fun loseExp(loosedExp : Int) {
        experienceCollected -= loosedExp
        experienceNeeded += loosedExp
        if (experienceCollected < 0) {
            level--
            experienceCollected = 0
        }
        updateDbUser()
    }

    suspend fun equipItem(toEquip: EquippableItem) {
        when (toEquip.blueprint.resolveType()) {
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

    suspend fun useUsableitem(toUse: UsableItem) {
        currentHealth += toUse.healthRecovery
        if (currentHealth > maxHealth) currentHealth = maxHealth
        // TODO: think if you want to add more features to the usableItems
        updateDbUser()
    }

    suspend fun completedBattle(hasWon: Boolean) {
        nBattles++
        if (hasWon) {
            nWonBattles++
            this.collectExp(250)
            return
        }else{
            this.loseExp(1000)
            currentHealth -= (currentHealth * 0.35).toInt()
        }
        updateDbUser()
    }

    fun getDamageDealt(): Int {
        var damageDealt = damage
        if (weapon != null) damageDealt += weapon!!.getActualDamage()
        if (helmet != null) damageDealt += helmet!!.getActualDamage()
        if (rune != null) damageDealt += rune!!.getActualDamage()
        return damageDealt
    }

    fun getHealth(): Int {
        var totalHealth = currentHealth
        if (weapon != null) totalHealth += weapon!!.getActualHealth()
        if (helmet != null) totalHealth += helmet!!.getActualHealth()
        if (rune != null) totalHealth += rune!!.getActualHealth()
        return totalHealth
    }
}
