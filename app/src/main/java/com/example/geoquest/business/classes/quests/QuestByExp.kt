package com.example.geoquest.business.classes.quests

import android.content.Context
import com.example.geoquest.R
import com.example.geoquest.business.models.EquippableItem
import com.example.geoquest.business.models.Player
import com.example.geoquest.business.models.UsableItem
import com.example.geoquest.utilities.PreferenceManager

class QuestByExp(
    val context: Context,
    val playerExpAtQuestStart: Int,
    firstChoice: EquippableItem,
    secondChoice: UsableItem,
    difficulty: Difficulties = extractRandomDifficulty(),
) : Quest(
    context.getString(R.string.questByFoot),
    firstChoice,
    secondChoice,
    (QuestManager.QuestByExpConfiguration.baseExperience * difficulty.multiplier).toInt(),
    difficulty
) {
    val maxProgress = calculateMaxProgress(QuestManager.QuestByExpConfiguration.baseMaxProgression)

    override suspend fun getProgress(): Int {
        val player = PreferenceManager.getObject("player", Player::class.java)
        if (player == null) throw Exception("PLAYER IS NULL")

        val currentProgress = player.experienceCollected - playerExpAtQuestStart
        return getProgress(currentProgress, maxProgress)
    }
}
