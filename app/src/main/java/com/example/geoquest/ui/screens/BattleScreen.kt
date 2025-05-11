package com.example.geoquest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CardGiftcard
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.geoquest.R
import com.example.geoquest.business.models.Player
import com.example.geoquest.ui.components.baseComponents.BigLoader
import com.example.geoquest.ui.components.baseComponents.ButtonProps
import com.example.geoquest.ui.components.baseComponents.CustomButton
import com.example.geoquest.ui.components.baseComponents.Divider
import com.example.geoquest.ui.components.dialogs.LeagueRewardDisplayDialog
import com.example.geoquest.ui.components.dialogs.RewardDialog
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getGradient
import com.example.geoquest.ui.theme.getSize
import com.example.geoquest.ui.viewModels.LeagueViewModel
import com.example.geoquest.utilities.ImagesResolver
import com.example.geoquest.utilities.PreferenceManager

fun init(): LeagueViewModel {
    return LeagueViewModel()
}

@Composable
fun SingleRow(player: Player, isHighlighted: Boolean, isDialogOpen: MutableState<Boolean>) {
    var modifierForRow = Modifier
        .fillMaxWidth()
        .height(85.dp)
        .padding(10.dp)

    if (isHighlighted)
        modifierForRow = modifierForRow.background(
            brush = getGradient()
        )



    Row(
        modifier = modifierForRow,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            player.name,
            fontSize = getSize(TextType.Normal),
            modifier = Modifier.padding(start = 7.dp),
            color = if (isHighlighted)
                MaterialTheme.colorScheme.background
            else
                MaterialTheme.colorScheme.primary
        )

        if (isHighlighted) {
            Icon(
                imageVector = Icons.Rounded.CardGiftcard,
                tint = MaterialTheme.colorScheme.background,
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 7.dp)
                    .clickable(
                        true,
                        onClick = {
                            isDialogOpen.value = true
                        }
                    )
            )
        }
    }
}

@Composable
fun BattleScreen(modifier: Modifier) {
    val leagueViewModel = remember { init() }
    val player = PreferenceManager.getObject("player", Player::class.java)
    val isLoading = remember { leagueViewModel.isLeagueLoading }
    val isRewardDialogOpen = remember { mutableStateOf(false) }
    val isLoadingReward = remember { leagueViewModel.isGettingReward }
    val generatedReward = remember { leagueViewModel.reward }

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

                    SingleRow(
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
                modifier = Modifier.offset(x = 0.dp, y = 130.dp),
                props = ButtonProps(
                    label = "BATTLE",
                    onClick = {}
                )
            )

        }
    }

    when{
        isLoadingReward.value -> {
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

        isRewardDialogOpen.value ->{
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

