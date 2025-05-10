package com.example.geoquest.ui.components.baseComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.geoquest.business.classes.quests.Quest
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getSize
import com.example.geoquest.R
import com.example.geoquest.business.classes.quests.QuestByExp
import com.example.geoquest.business.classes.quests.QuestByFoot
import com.example.geoquest.ui.components.dialogs.ChooseItemDialog
import com.example.geoquest.ui.viewModels.factories.GlobalViewModels

fun getMaxProgression(quest: Quest): Int {
    return if (quest is QuestByFoot) {
        quest.maxProgress
    } else if (quest is QuestByExp) {
        quest.maxProgress
    } else {
        return 0
    }
}

@Composable
fun SingleQuest(toShow: Quest,onGetQuest: () -> Unit) {
    val currentProgress = remember { mutableIntStateOf(0) }
    val rewardString = stringResource(R.string.rewards)
    val expTitle = stringResource(R.string.experience)
    val expDesc = stringResource(R.string.experienceDesc, toShow.experiencePrize)
    val getString = stringResource(R.string.get)
    val isDialogOpen = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        currentProgress.intValue = toShow.getProgress()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(toShow.name, fontSize = getSize(TextType.Normal), fontWeight = FontWeight.Bold)
            Text(
                toShow.difficulty.getDisplayName(LocalContext.current),
                fontSize = getSize(TextType.ButtonText),
                fontWeight = FontWeight.Thin
            )
        }
        Text(
            "${currentProgress.intValue}/${getMaxProgression(toShow)}",
            fontSize = getSize(TextType.Normal)
        )
        LinearProgressIndicator(
            progress = { currentProgress.intValue.toFloat()/100 },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(vertical = 10.dp)
                .clip(CircleShape),
            color = MaterialTheme.colorScheme.surfaceVariant,
            trackColor = MaterialTheme.colorScheme.secondary,
            strokeCap = StrokeCap.Square,
        )
        Text(rewardString, fontSize = getSize(TextType.Title), fontWeight = FontWeight.Bold)
        Column {
            SingleQuestItem(
                item = toShow.firstChoice,
                modifier = Modifier
            )
            SingleQuestItem(
                item = toShow.secondChoice,
                modifier = Modifier
            )
            RewardRow(
                modifier = Modifier,
                image = {
                    ExperienceImage(
                        modifier = Modifier.size(75.dp)
                    )
                },
                title = expTitle,
                desc = expDesc,
                rarityColor = null
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            CustomButton(
                props = ButtonProps(
                    label = getString,
                    isEnabled = true,//currentProgress.intValue == 100,
                    onClick = {
                        isDialogOpen.value = true
                    }
                )
            )
        }
    }

    when{
        isDialogOpen.value -> {
            ChooseItemDialog(
                firstChoice = toShow.firstChoice,
                secondChoice = toShow.secondChoice,
                onDismissRequest = {
                    isDialogOpen.value = false
                },
                onAccept = {
                    isDialogOpen.value = false
                    onGetQuest()
                    GlobalViewModels.navBarViewModel.triggerAnimation()
                }
            )
        }
    }

}