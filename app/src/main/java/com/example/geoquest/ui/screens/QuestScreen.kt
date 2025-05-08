package com.example.geoquest.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.geoquest.ui.viewModels.QuestManager
import com.example.geoquest.business.models.Player
import com.example.geoquest.ui.components.baseComponents.BigLoader
import com.example.geoquest.ui.components.baseComponents.Divider
import com.example.geoquest.ui.components.baseComponents.SingleCompletedQuest
import com.example.geoquest.ui.components.layout.MissionsTabRow
import com.example.geoquest.utilities.PreferenceManager

@Composable
fun QuestScreen(modifier: Modifier) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val questManager = QuestManager(context)
    val isLoading = remember { questManager.isLoadingQuests }



    Column(
        modifier = modifier
    ) {
        MissionsTabRow(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        if (isLoading.value) {
            BigLoader()
        } else {
            when (selectedTab) {
                0 -> Text("CURRENT QUESTS")
                1 -> {
                    questManager.getCompletedQuests(
                        PreferenceManager.getObject(
                            "player",
                            Player::class.java
                        )!!.id
                    )
                    val completed = remember { questManager.completedQuest }

                    LazyColumn(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        items(completed.value) { quest ->
                            if (completed.value.first().id == quest.id) {
                                Divider(modifier = Modifier)
                            }

                            SingleCompletedQuest(toShow = quest)
                            Divider(modifier = Modifier)
                        }
                    }
                }

            }
        }
    }
}
