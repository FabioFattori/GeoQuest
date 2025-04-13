package com.example.geoquest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.geoquest.ui.components.Select
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getSize
import com.example.geoquest.ui.viewModels.ThemeViewModel
import com.example.geoquest.utilities.PreferenceManager

private fun handleThemeChange(selectedOption: String, themeViewModel: ThemeViewModel) {
    var isDark = false
    if (selectedOption == "Scuro") {
        isDark = true
    }


    themeViewModel.toggleTheme()

    PreferenceManager.saveTheme(isDark)
}

@Composable
fun SettingsScreen(modifier: Modifier) {

    val themeOptions = remember {
        listOf(
            "Chiaro",
            "Scuro",
        )
    }

    val themeViewModel: ThemeViewModel = viewModel()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.height(70.dp)
        ) {
            Text("Tema", fontSize = getSize(TextType.Normal), modifier = Modifier.fillMaxHeight())
            Select(
                modifier = Modifier
                    .width(
                        150.dp
                    )
                    .fillMaxHeight(),
                options = themeOptions,
                onOptionSelected = { selectedOption ->
                    handleThemeChange(
                        selectedOption,
                        themeViewModel
                    )
                },
                preselectedOption = if (!PreferenceManager.getTheme()) "Chiaro" else "Scuro",
            )
        }
    }
}
