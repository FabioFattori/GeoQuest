package com.example.geoquest.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.geoquest.GameActivity
import com.example.geoquest.business.models.Player
import com.example.geoquest.R
import com.example.geoquest.ui.components.baseComponents.BattleUser
import com.example.geoquest.ui.components.baseComponents.Divider
import com.example.geoquest.ui.components.baseComponents.ExperienceImage
import com.example.geoquest.ui.components.dialogs.DialogMode
import com.example.geoquest.ui.components.dialogs.RewardDialog
import com.example.geoquest.ui.components.dialogs.SimpleDialog
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getSize
import com.example.geoquest.ui.viewModels.BattleHandlerViewModel

private fun init(player: Player, opponent: Player): BattleHandlerViewModel {
    return BattleHandlerViewModel(
        player = player,
        opponent = opponent
    )
}

private fun goBackToMainMenu(context: Context) {
    val intent = Intent(context, GameActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}

@Composable
fun BattleScreen(opponent: Player, player: Player) {
    val titleString = stringResource(R.string.battleTitle, opponent.name)
    val registryString = stringResource(R.string.battleRegistry)
    val battleHandler = remember { init(player, opponent) }
    val playerCurrentHealth = battleHandler.playerHealth.collectAsState()
    val opponentCurrentHealth = battleHandler.opponentHealth.collectAsState()
    val registry = battleHandler.battleRegistry.collectAsState()
    val isBattleOver = battleHandler.isBattleOver.collectAsState()
    val winner = battleHandler.winner.collectAsState()

    // start the battle
    LaunchedEffect(Unit) {
        battleHandler.battle()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),

            ) {
            Box(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    titleString,
                    fontWeight = FontWeight.Bold,
                    fontSize = getSize(TextType.Title),
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }
            BattleUser(
                playerName = opponent.name,
                isAlignedToLeft = false,
                currentHealth = opponentCurrentHealth.value,
                maxHealth = opponent.getHealth()
            )

            Text(
                registryString,
                modifier = Modifier.padding(top = 30.dp),
                fontWeight = FontWeight.Bold,
                fontSize = getSize(TextType.Title),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp)
                    .height(400.dp)
            ) {
                items(registry.value) { item ->
                    item.GetText()
                    Divider(
                        modifier = Modifier
                    )
                }
            }

            BattleUser(
                playerName = player.name,
                isAlignedToLeft = true,
                currentHealth = playerCurrentHealth.value,
                maxHealth = player.getHealth()
            )
        }
    }

    when {
        isBattleOver.value -> {
            val context = LocalContext.current
            if (winner.value == null) {
                return
            }
            if (winner.value!!.id == player.id) {
                val winBattle = stringResource(R.string.wonBattle)
                RewardDialog(
                    onConfirmation = {
                        battleHandler.endBattle()
                        goBackToMainMenu(context)
                    },
                    image = {
                        ExperienceImage(
                            modifier = Modifier.size(70.dp)
                        )
                    },
                    title = winBattle,
                    rarity = null
                )
            } else {
                val loseBattle = stringResource(R.string.loseBattle)
                SimpleDialog(
                    text = loseBattle,
                    onNo = null,
                    onOk = {
                        battleHandler.endBattle()
                        goBackToMainMenu(context)
                    },
                    dialogMode = DialogMode.Info
                )
            }
        }
    }
}
