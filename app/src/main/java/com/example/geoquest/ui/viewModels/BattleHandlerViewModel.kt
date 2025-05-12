package com.example.geoquest.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geoquest.business.classes.battle.BattleRegistryEntry
import com.example.geoquest.business.classes.battle.MessageEntry
import com.example.geoquest.business.classes.battle.SingleEntry
import com.example.geoquest.business.classes.battle.enums.MessageTypes
import com.example.geoquest.business.models.Player
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BattleHandlerViewModel(
    val player: Player,
    val opponent: Player,
) : ViewModel() {
    private val waitingTime = 2500L

    private val _battleRegistry = MutableStateFlow<List<SingleEntry>>(emptyList())
    val battleRegistry = _battleRegistry

    val playerHealth = MutableStateFlow(player.getHealth())
    val opponentHealth = MutableStateFlow(opponent.getHealth())

    private val _isBattleOver = MutableStateFlow(false)
    val isBattleOver = _isBattleOver

    private val _winner = MutableStateFlow<Player?>(null)
    val winner = _winner

    private fun checkBattleIsOver(defender: Player, attacker: Player): Boolean {
        if (defender.id == player.id) {
            if (playerHealth.value <= 0) {
                _isBattleOver.value = true
                _winner.value = attacker
                return true
            }
        } else {
            if (opponentHealth.value <= 0) {
                _isBattleOver.value = true
                _winner.value = attacker
                return true
            }
        }
        return false
    }

    fun battle() {
        viewModelScope.launch {
            var attacker = player
            var defender = opponent

            _battleRegistry.value += MessageEntry(
                messageType = MessageTypes.BattleStart
            )

            delay(waitingTime + 1000)

            while (playerHealth.value > 0 && opponentHealth.value > 0) {
                val damage = attacker.getDamageDealt()

                // check to which player the damage is dealt
                if (attacker.id == player.id) {
                    if (opponentHealth.value - damage < 0) {
                        opponentHealth.value = 0
                    } else {
                        opponentHealth.value -= damage
                    }
                } else {
                    if (playerHealth.value - damage < 0) {
                        playerHealth.value = 0
                    } else {
                        playerHealth.value -= damage
                    }
                }

                val entry = BattleRegistryEntry(
                    attackerName = attacker.name,
                    damageDealt = damage,
                    attackedName = defender.name,
                    isPlayer = attacker.id == player.id
                )

                _battleRegistry.value += entry

                if (checkBattleIsOver(defender, attacker)) {
                    _battleRegistry.value += MessageEntry(
                        messageType = MessageTypes.BattleEnd
                    )
                    break
                }

                // switch turns
                val tmp = attacker
                attacker = defender
                defender = tmp

                delay(waitingTime) // Wait 2.5 seconds before next turn
            }
        }
    }

    fun endBattle() {
        viewModelScope.launch {
            player.completedBattle(_winner.value!!.id == player.id)
            _isBattleOver.value = true
            _winner.value = null
        }
    }
}

