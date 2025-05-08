package com.example.geoquest.ui.viewModels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geoquest.apiService.ApiService
import com.example.geoquest.business.classes.quests.Quest
import com.example.geoquest.business.classes.quests.QuestBaseConfiguration
import com.example.geoquest.business.classes.quests.QuestByExp
import com.example.geoquest.business.classes.quests.QuestByFoot
import com.example.geoquest.business.models.CompletedQuest
import com.example.geoquest.business.models.Player
import com.example.geoquest.utilities.PreferenceManager
import kotlinx.coroutines.launch

enum class PossibleQuests {
    QuestByFoot,
    QuestByExp
}

class QuestManager(context: Context) : ViewModel() {
    val questsForPlayer = mutableListOf<Quest>()
    val isLoadingQuests = mutableStateOf(false)
    val completedQuest = mutableStateOf<List<CompletedQuest>>(emptyList())

    init {
        isLoadingQuests.value = true
        PreferenceManager.getQuests(context) { lst ->
            questsForPlayer.addAll(lst)
            generateQuests(
                PreferenceManager.getObject("player", Player::class.java)!!,
                context
            )
        }
    }

    fun getCompletedQuests(playerId: Int) {
        isLoadingQuests.value = true
        viewModelScope.launch {
            try {
                val response = ApiService.retrofit.getAllCompletedQuests(playerId)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body == null) {
                        isLoadingQuests.value = false
                        throw Exception("got null body")
                    }
                    completedQuest.value = body
                    isLoadingQuests.value = false
                }
            } catch (ex: Exception) {
                isLoadingQuests.value = false
                throw ex
            }
        }
    }

    fun generateQuests(player: Player, context: Context) {
        val generator = CreateRewardViewModel()

        for (i in 1..MAX_ACTIVE_QUESTS - questsForPlayer.size) {
            generator.clearGeneratedEquippableItem()
            generator.clearGeneratedUsableItem()

            generator.createReward(GenerateType.EquippableItem, onFinished = {
                val equippableItem = generator.generatedEquippableItem.value

                generator.createReward(GenerateType.UsableItem, onFinished = {
                    val usableItem = generator.generatedUsableItem.value

                    if (equippableItem != null && usableItem != null) {
                        val questType = extractRandomQuestType()

                        val newQuest = when (questType) {
                            PossibleQuests.QuestByFoot -> QuestByFoot(
                                context,
                                firstChoice = equippableItem,
                                secondChoice = usableItem
                            )

                            PossibleQuests.QuestByExp -> QuestByExp(
                                context,
                                firstChoice = equippableItem,
                                secondChoice = usableItem,
                                playerExpAtQuestStart = player.experienceCollected
                            )
                        }

                        questsForPlayer.add(newQuest)
                        PreferenceManager.saveQuests(questsForPlayer)
                        if (questsForPlayer.size == MAX_ACTIVE_QUESTS) isLoadingQuests.value = false
                    } else {
                        throw Exception("something is null => eq ${equippableItem}, usa ${usableItem}")
                    }
                }, giveItemToPlayer = false)
            }, giveItemToPlayer = false)
        }
        if (questsForPlayer.size == MAX_ACTIVE_QUESTS) isLoadingQuests.value = false

    }

    private fun extractRandomQuestType(): PossibleQuests {
        val possibleValues = PossibleQuests.entries.toTypedArray()
        return possibleValues[(0..(possibleValues.size - 1)).random()]
    }


    companion object {
        const val MAX_ACTIVE_QUESTS = 5
        val QuestByFootConfiguration = QuestBaseConfiguration(1000, 700)
        val QuestByExpConfiguration = QuestBaseConfiguration(2000, 1000)
    }
}
