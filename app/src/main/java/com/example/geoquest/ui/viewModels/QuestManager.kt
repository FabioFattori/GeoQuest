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
    val questsForPlayer = mutableStateOf<List<Quest>>(emptyList())
    val isLoadingQuests = mutableStateOf(false)
    val completedQuest = mutableStateOf<List<CompletedQuest>>(emptyList())

    init {
        isLoadingQuests.value = true
        PreferenceManager.getQuests(context) { lst ->
            questsForPlayer.value = lst
            generateQuests(
                PreferenceManager.getObject("player", Player::class.java)!!,
                context
            )
        }
    }

    fun removeQuest(toRemove : Quest){
        questsForPlayer.value -= toRemove
        PreferenceManager.saveQuests(questsForPlayer.value)
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
        var nGenerationCompleted = questsForPlayer.value.size

        for (i in 1..MAX_ACTIVE_QUESTS - questsForPlayer.value.size) {
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
                                secondChoice = usableItem,
                                alreadyMadeProgress = 0
                            )

                            PossibleQuests.QuestByExp -> QuestByExp(
                                context,
                                firstChoice = equippableItem,
                                secondChoice = usableItem,
                                playerExpAtQuestStart = player.experienceCollected
                            )
                        }
                        nGenerationCompleted++
                        questsForPlayer.value += newQuest
                        PreferenceManager.saveQuests(questsForPlayer.value)
                        if (questsForPlayer.value.size == MAX_ACTIVE_QUESTS) isLoadingQuests.value = false
                    } else {
                        throw Exception("something is null => eq ${equippableItem}, usa ${usableItem}")
                    }
                }, giveItemToPlayer = false)
            }, giveItemToPlayer = false)
        }
        if (questsForPlayer.value.size == MAX_ACTIVE_QUESTS || nGenerationCompleted == MAX_ACTIVE_QUESTS)
            isLoadingQuests.value = false

    }

    private fun extractRandomQuestType(): PossibleQuests {
        return PossibleQuests.entries.random()
    }


    companion object {
        const val MAX_ACTIVE_QUESTS = 2
        val QuestByFootConfiguration = QuestBaseConfiguration(1000, 700)
        val QuestByExpConfiguration = QuestBaseConfiguration(2000, 1000)
    }
}
