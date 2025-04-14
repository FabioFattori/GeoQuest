package com.example.geoquest.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.geoquest.utilities.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(navigator: NavHostController, iconSize: Dp) {
    TopAppBar(
        expandedHeight = 120.dp,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 0.dp,
                        end = 20.dp
                    )
            ) {
                IconButton(
                    onClick = { navigator.navigate(Screens.Profile.route) }
                ) {
                    IconGradient(
                        icon = Icons.Rounded.AccountCircle,
                        contentDescription = "profileIcon",
                        modifier = Modifier.size(iconSize)
                    )
                }

                PlayerBadgeForTopBar(modifier = Modifier)

                IconButton(
                    onClick = { navigator.navigate(Screens.Settings.route) }
                ) {
                    IconGradient(
                        icon = Icons.Rounded.Settings,
                        contentDescription = "settingsIcon",
                        modifier = Modifier.size(iconSize)
                    )
                }
            }
        }
    )
}
