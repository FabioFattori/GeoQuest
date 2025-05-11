package com.example.geoquest.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.sharp.Diamond
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.geoquest.ui.components.baseComponents.ButtonProps
import com.example.geoquest.ui.components.baseComponents.CustomButton
import com.example.geoquest.ui.components.baseComponents.IconGradient
import com.example.geoquest.ui.viewModels.LeagueViewModel
import com.example.geoquest.R
import com.example.geoquest.business.models.enums.RewardResponse
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getSize

@Composable
fun LeagueRewardDisplayDialog(
    leagueViewModel: LeagueViewModel,
    onDismissRequest: () -> Unit,
    onAccept: () -> Unit
) {
    val titleString = stringResource(R.string.leagueReward)
    val descString = stringResource(R.string.leagueDesc)
    val posString = stringResource(R.string.leaguePos)
    val gradeString = stringResource(R.string.gradeReward)
    val canGetString = stringResource(
        when (leagueViewModel.canGetRewardResult.value) {
            RewardResponse.PlayerCanGetReward -> R.string.canGetReward
            RewardResponse.AlreadyGotReward -> R.string.alreadyGotReward
            RewardResponse.PlayerHasNotDoneEnoughBattles -> R.string.notEnoughBattles
            else -> R.string.Impossible
        }
    )
    val getString = stringResource(R.string.get)

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .then(Modifier.widthIn(min = 1600.dp))
                .padding(15.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    IconButton(
                        onClick = onDismissRequest
                    ) {
                        IconGradient(
                            modifier = Modifier.size(100.dp),
                            contentDescription = "",
                            icon = Icons.Rounded.Close
                        )
                    }
                }

                Text(
                    titleString,
                    fontSize = getSize(TextType.Normal),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    descString,
                    fontSize = getSize(TextType.ButtonText)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.width(100.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            posString,
                            fontSize = getSize(TextType.ButtonText),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            leagueViewModel.playerPosition.intValue.toString(),
                            fontSize = getSize(TextType.Title),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Column(
                        modifier = Modifier.width(150.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            gradeString,
                            fontSize = getSize(TextType.ButtonText),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            leagueViewModel.getRarityNameOfReward(),
                            fontWeight = FontWeight.Bold,
                            fontSize = getSize(
                                TextType.Title
                            )
                        )
                        Icon(
                            imageVector = Icons.Sharp.Diamond,
                            modifier = Modifier.size(40.dp),
                            tint = leagueViewModel.getRarityColorOfReward(),
                            contentDescription = ""
                        )
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        canGetString,
                        fontWeight = FontWeight.Bold,
                        fontSize = getSize(TextType.ButtonText),
                        textAlign = TextAlign.Center
                    )
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CustomButton(
                        props = ButtonProps(
                            label = getString,
                            onClick = onAccept,
                            isEnabled = leagueViewModel.canGetRewardResult.value == RewardResponse.PlayerCanGetReward
                        ),
                        modifier = Modifier.padding(8.dp),
                    )
                }

            }
        }
    }
}