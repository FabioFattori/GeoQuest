package com.example.geoquest.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.geoquest.BattleActivity
import com.example.geoquest.business.models.Player
import com.example.geoquest.ui.components.baseComponents.BigLoader
import com.example.geoquest.ui.components.baseComponents.ButtonProps
import com.example.geoquest.ui.components.baseComponents.CustomButton
import com.example.geoquest.ui.components.baseComponents.Divider
import com.example.geoquest.ui.components.baseComponents.SingleUserRow
import com.example.geoquest.ui.components.dialogs.LeagueRewardDisplayDialog
import com.example.geoquest.ui.components.dialogs.RewardDialog
import com.example.geoquest.ui.viewModels.LeagueViewModel
import com.example.geoquest.utilities.ImagesResolver
import com.example.geoquest.utilities.PreferenceManager
import com.example.geoquest.R

fun init(): LeagueViewModel {
    return LeagueViewModel()
}

@Composable
fun LeagueScreen(modifier: Modifier) {
    val leagueViewModel = remember { init() }
    val player = PreferenceManager.getObject("player", Player::class.java)
    val isLoading = remember { leagueViewModel.isLeagueLoading }
    val isRewardDialogOpen = remember { mutableStateOf(false) }
    val isLoadingReward = remember { leagueViewModel.isGettingReward }
    val generatedReward = remember { leagueViewModel.reward }
    val isAskingForOpponent = remember { leagueViewModel.isAskingForOpponent }
    val findMatchString = stringResource(R.string.findMatch)
    val context = LocalContext.current

    if (player == null) throw Exception("player is null")
    LaunchedEffect(Unit) {
        leagueViewModel.getLeague()
    }

    if (isLoading.value) {
        BigLoader()
    } else {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            LazyColumn {
                items(leagueViewModel.playersInLeague) { singlePlayer ->

                    SingleUserRow(
                        player = singlePlayer,
                        isHighlighted = singlePlayer.id == player.id,
                        isDialogOpen = isRewardDialogOpen
                    )

                    if (leagueViewModel.playersInLeague.indexOf(singlePlayer) != leagueViewModel.playersInLeague.size - 1) {
                        Divider(
                            modifier = modifier.padding(horizontal = 10.dp)
                        )
                    }
                }
            }

            CustomButton(
                modifier = Modifier
                    .offset(x = 0.dp, y = 130.dp),
                props = ButtonProps(
                    label = findMatchString,
                    buttonSize = 240.dp,
                    onClick = {
                        leagueViewModel.getOpponent(
                            onFinished = { opponent ->
                                val intent = Intent(context, BattleActivity::class.java).apply {
                                    putExtra("opponent", opponent)
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                }
                                context.startActivity(intent)
                            }
                        )
                    }
                )
            )

        }
    }

    when {
        isLoadingReward.value || isAskingForOpponent.value -> {
            AlertDialog(
                onDismissRequest = {},
                text = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                },
                confirmButton = {}
            )
        }

        generatedReward.value != null -> {
            val item = generatedReward.value!!
            RewardDialog(
                onConfirmation = {
                    generatedReward.value = null
                },
                image = {
                    val images = ImagesResolver.associateDbImagesToPossibleImages()
                    images[item.blueprint.imagePath]?.let { image ->
                        ImagesResolver.GetImageComponent(image)
                    }
                },
                rarity = item.rarity,
                title = item.blueprint.name
            )
        }

        isRewardDialogOpen.value -> {
            LeagueRewardDisplayDialog(
                leagueViewModel,
                onDismissRequest = {
                    isRewardDialogOpen.value = false
                },
                onAccept = {
                    isRewardDialogOpen.value = false
                    leagueViewModel.getReward()
                }
            )

        }
    }
}

