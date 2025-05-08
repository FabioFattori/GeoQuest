package com.example.geoquest.business.classes.quests

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.example.geoquest.R
import com.example.geoquest.business.models.EquippableItem
import com.example.geoquest.business.models.UsableItem
import com.example.geoquest.ui.viewModels.InventoryViewModel
import com.example.geoquest.ui.viewModels.QuestManager
import org.json.JSONObject
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class QuestByFoot(
    val context: Context,
    firstChoice: EquippableItem,
    secondChoice: UsableItem,
    difficulty: Difficulties = extractRandomDifficulty()
) : Quest(
    context.getString(R.string.questByFoot),
    firstChoice,
    secondChoice,
    (QuestManager.QuestByFootConfiguration.baseExperience * difficulty.multiplier).toInt(),
    difficulty
) {
    val maxProgress = calculateMaxProgress(QuestManager.QuestByFootConfiguration.baseMaxProgression)

    override suspend fun getProgress(): Int {
        val healthConnectClient = HealthConnectClient.getOrCreate(context)

        val startTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()
        val endTime = Instant.now()

        val response = healthConnectClient.readRecords(
            ReadRecordsRequest<StepsRecord>(
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
        )

        val currentProgress = response.records.sumOf { it.count }

        return this.getProgress(currentProgress.toInt(), maxProgress)
    }

    override fun toJson(): JSONObject {
        val json = super.toJson()
        json.put("type", "FootQuest")
        return json
    }

    companion object{
        fun fromJson(json: JSONObject, context: Context, onFinished: (QuestByFoot) -> Unit) {
            val getter = InventoryViewModel()

            getter.getUsableItemById(json.getInt("secondChoiceId")) { usable ->
                if (usable == null) return@getUsableItemById // handle error
                getter.getEquippableItemById(json.getInt("firstChoiceId")) { equippable ->
                    if (equippable == null) return@getEquippableItemById // handle error
                    val difficulty = Difficulties.valueOf(json.getString("difficulty"))

                    val quest = QuestByFoot(
                        context = context,
                        firstChoice = equippable,
                        secondChoice = usable,
                        difficulty = difficulty
                    )
                    onFinished(quest)
                }
            }

        }
    }
}
