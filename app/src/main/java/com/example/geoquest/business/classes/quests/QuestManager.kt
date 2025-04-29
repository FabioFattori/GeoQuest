package com.example.geoquest.business.classes.quests

import android.content.Context
import com.example.geoquest.business.models.Player

enum class PossibleQuests {
    QuestByFoot,
    QuestByExp
}

class QuestManager {
    val maxActiveQuests = 5
    val questsForPlayer: List<Quest> = emptyList()

    fun generateQuests(player: Player, context: Context) {
        for (i in 1..maxActiveQuests) {
            val questType = extractRandomQuestType()
            val newQuest: Quest

            when (questType) {
                PossibleQuests.QuestByFoot -> {

                }

                PossibleQuests.QuestByExp -> {

                }
            }
        }
    }

    private fun extractRandomQuestType(): PossibleQuests {
        val possibleValues = PossibleQuests.entries.toTypedArray()
        return possibleValues[(0..(possibleValues.size - 1)).random()]
    }


    companion object {
        val QuestByFootConfiguration = QuestBaseConfiguration(1000, 700)
        val QuestByExpConfiguration = QuestBaseConfiguration(2000, 1000)
    }
}
