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
    var alreadyMadeProgress : Int,
    difficulty: Difficulties = extractRandomDifficulty()
) : Quest(
    context.getString(R.string.questByFoot),
    firstChoice,
    secondChoice,
    (QuestManager.QuestByFootConfiguration.baseExperience * difficulty.multiplier).toInt(),
    difficulty
) {
    val maxProgress = calculateMaxProgress(QuestManager.QuestByFootConfiguration.baseMaxProgression)
    override fun getCurrentProgressNumber(): Int {
        return alreadyMadeProgress
    }

    override suspend fun getProgress(): Int {
        val healthConnectClient = HealthConnectClient.getOrCreate(context)

        val granted = healthConnectClient.permissionController.getGrantedPermissions()
        val requiredPermission = androidx.health.connect.client.permission.HealthPermission.getReadPermission(StepsRecord::class)

        if (requiredPermission !in granted) {
            return 0
        }

        val startTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()
        val endTime = Instant.now()

        val response = healthConnectClient.readRecords(
            ReadRecordsRequest(
                recordType = StepsRecord::class,
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
        )

        val currentProgress = response.records.sumOf { it.count } + alreadyMadeProgress
        alreadyMadeProgress = currentProgress.toInt()
        return this.getProgress(currentProgress.toInt(), maxProgress)
    }


    override fun toJson(): JSONObject {
        val json = super.toJson()
        json.put("type", "FootQuest")
        json.put("progress",alreadyMadeProgress)
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
                        difficulty = difficulty,
                        alreadyMadeProgress = json.getInt("progress")
                    )
                    onFinished(quest)
                }
            }

        }
    }
}
