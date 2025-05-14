package com.example.geoquest.business.classes.quests

import android.content.Context
import com.example.geoquest.R
import com.example.geoquest.business.models.EquippableItem
import com.example.geoquest.business.models.Player
import com.example.geoquest.business.models.UsableItem
import com.example.geoquest.ui.viewModels.InventoryViewModel
import com.example.geoquest.ui.viewModels.QuestManager
import com.example.geoquest.utilities.PreferenceManager
import org.json.JSONObject
import kotlin.math.min

class QuestByExp(
    val context: Context,
    val playerExpAtQuestStart: Int,
    firstChoice: EquippableItem,
    secondChoice: UsableItem,
    difficulty: Difficulties = extractRandomDifficulty(),
) : Quest(
    context.getString(R.string.questByExp),
    firstChoice,
    secondChoice,
    (QuestManager.QuestByExpConfiguration.baseExperience * difficulty.multiplier).toInt(),
    difficulty
) {
    val maxProgress = calculateMaxProgress(QuestManager.QuestByExpConfiguration.baseMaxProgression)

    override fun toJson(): JSONObject {
        val json = super.toJson()
        json.put("type", "ExpQuest")
        json.put("progress", getCurrentProgressNumber())
        return json
    }

    override fun getCurrentProgressNumber(): Int {
        val player = PreferenceManager.getObject("player", Player::class.java)
        if (player == null) throw Exception("PLAYER IS NULL")
        return min(player.experienceCollected - playerExpAtQuestStart, maxProgress)
    }

    override suspend fun getProgress(): Int {
        val toRet = getProgress(getCurrentProgressNumber(), maxProgress)
        return toRet
    }

    companion object {
        fun fromJson(json: JSONObject, context: Context, onFinished: (QuestByExp) -> Unit) {
            val getter = InventoryViewModel()

            getter.getUsableItemById(json.getInt("secondChoiceId")) { usable ->
                if (usable == null) return@getUsableItemById // handle error
                getter.getEquippableItemById(json.getInt("firstChoiceId")) { equippable ->
                    if (equippable == null) return@getEquippableItemById // handle error
                    val difficulty = Difficulties.valueOf(json.getString("difficulty"))

                    val quest = QuestByExp(
                        context = context,
                        firstChoice = equippable,
                        secondChoice = usable,
                        playerExpAtQuestStart = json.getInt("exp"),
                        difficulty = difficulty
                    )
                    onFinished(quest)
                }
            }

        }
    }
}
