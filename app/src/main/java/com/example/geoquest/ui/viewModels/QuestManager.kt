package com.example.geoquest.ui.viewModels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geoquest.apiService.ApiService
import com.example.geoquest.apiService.dto.requests.CreateCompletedQuestRequest
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
    val isLoadingCompletedQuests = mutableStateOf(false)
    val completedQuest = mutableStateOf<List<CompletedQuest>>(emptyList())

    init {
        isLoadingQuests.value = true
        PreferenceManager.getQuests(context) { lst ->
            questsForPlayer.value = lst
            generateQuests(
                PreferenceManager.getObject("player", Player::class.java)!!,
                context
            )

            isLoadingQuests.value = false
        }
    }

    fun removeQuest(toRemove: Quest) {
        questsForPlayer.value -= toRemove
        PreferenceManager.saveQuests(questsForPlayer.value)
        val player = PreferenceManager.getObject("player", Player::class.java)
        if (player == null) throw Exception("player == null")
        viewModelScope.launch {
            player.collectExp(toRemove.experiencePrize)
            ApiService.retrofit.createCompletedQuest(
                data = CreateCompletedQuestRequest(
                    playerId = player.id,
                    name = toRemove.name
                )
            )
        }
    }

    fun getCompletedQuests(playerId: Int) {
        isLoadingCompletedQuests.value = true
        viewModelScope.launch {
            try {
                val response = ApiService.retrofit.getAllCompletedQuests(playerId)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body == null) {
                        isLoadingCompletedQuests.value = false
                        throw Exception("got null body")
                    }
                    completedQuest.value = body.sortedByDescending {
                        it.id
                    }
                    isLoadingCompletedQuests.value = false
                }
            } catch (ex: Exception) {
                isLoadingCompletedQuests.value = false
                throw ex
            }
        }
    }

    fun generateQuests(player: Player, context: Context) {
        val generator = CreateRewardViewModel()

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
                        questsForPlayer.value += newQuest
                        PreferenceManager.saveQuests(questsForPlayer.value)
                    } else
                        throw Exception("something is null => eq $equippableItem, usa $usableItem")

                }, giveItemToPlayer = false)
            }, giveItemToPlayer = false)
        }

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
