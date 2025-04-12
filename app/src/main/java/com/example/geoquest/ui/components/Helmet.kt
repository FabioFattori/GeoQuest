package com.example.geoquest.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.geoquest.utilities.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Helmet(
    modifier: Modifier = Modifier,
    navigator: NavHostController,
    content: @Composable (Modifier) -> Unit,
) {
    val iconSize = 60.dp

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
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
        },
        bottomBar = {
        }
    ) { innerPadding ->


        // content handling
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            content(modifier)
        }

        // bottom bar

    }
}
