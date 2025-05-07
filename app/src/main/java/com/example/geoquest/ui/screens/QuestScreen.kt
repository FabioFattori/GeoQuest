package com.example.geoquest.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.geoquest.business.classes.quests.QuestManager
import com.example.geoquest.ui.components.layout.MissionsTabRow

@Composable
fun QuestScreen(modifier: Modifier) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val questManager = QuestManager()

    Column(
        modifier = modifier
    ){
        MissionsTabRow(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        when (selectedTab) {
            0 -> Text("CURRENT QUESTS")
            1 -> Text("Completed QUESTS")
        }
    }
}
