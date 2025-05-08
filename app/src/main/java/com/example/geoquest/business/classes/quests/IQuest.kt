package com.example.geoquest.business.classes.quests

import com.example.geoquest.business.models.EquippableItem
import com.example.geoquest.business.models.UsableItem
import org.json.JSONObject

data class CollectedPrize(
    val exp: Int,
    var chosenItem: Any?,
)

data class AllRewards(
    val exp: Int,
    val equippableItem: EquippableItem,
    val usableItem: UsableItem
)

interface IQuest {
    fun collectPrize(firstIsChosen: Boolean): CollectedPrize
    fun displayPrizes(): AllRewards
    suspend fun getProgress(): Int
    fun toJson() : JSONObject
}
