package com.example.geoquest.business.classes.quests

import android.content.Context
import com.example.geoquest.R
import com.example.geoquest.business.models.EquippableItem
import com.example.geoquest.business.models.UsableItem
import org.json.JSONObject
import kotlin.math.min

enum class Difficulties(val multiplier: Double) {
    Simple(0.65), Medium(1.0), Hard(1.25), Impossible(2.5);

    fun getDisplayName(context: Context): String {
        return when (this) {
            Simple -> context.getString(R.string.Simple)
            Medium -> context.getString(R.string.Normal)
            Hard -> context.getString(R.string.Hard)
            Impossible -> context.getString(R.string.Impossible)
        }
    }
}

abstract class Quest(
    val name: String,
    val firstChoice: EquippableItem,
    val secondChoice: UsableItem,
    val experiencePrize: Int,
    val difficulty: Difficulties
) : IQuest {

    override fun collectPrize(firstIsChosen: Boolean): CollectedPrize {
        val toRet = CollectedPrize(
            exp = experiencePrize,
            chosenItem = null
        )
        if (firstIsChosen) {
            toRet.chosenItem = firstChoice
        } else {
            toRet.chosenItem = secondChoice
        }

        return toRet
    }

    override fun displayPrizes(): AllRewards {
        return AllRewards(
            exp = experiencePrize,
            equippableItem = firstChoice,
            usableItem = secondChoice
        )
    }

    protected fun calculateMaxProgress(maxProgression: Int): Int {
        return (difficulty.multiplier * maxProgression).toInt()
    }

    fun getProgress(currentProgress: Int, maxProgress: Int): Int {
        return min((currentProgress * 100 / maxProgress).toInt(), 100)
    }

    override fun toJson(): JSONObject {
        val json = JSONObject()
        json.put("exp", experiencePrize)
        json.put("firstChoiceId", firstChoice.id)
        json.put("secondChoiceId", secondChoice.id)
        json.put("difficulty", difficulty.name)
        return json
    }

    companion object {
        @JvmStatic
        protected fun extractRandomDifficulty(): Difficulties {
            val possibleValues = Difficulties.entries.toTypedArray()
            return possibleValues[(0..(possibleValues.size - 1)).random()]
        }
    }
}
