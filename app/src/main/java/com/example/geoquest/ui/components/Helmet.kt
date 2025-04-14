package com.example.geoquest.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

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
            CustomTopAppBar(
                iconSize = iconSize,
                navigator = navigator
            )
        },
        bottomBar = {
            CustomBottomBar(
                modifier = Modifier.fillMaxWidth(),
                navigator = navigator,
            )
        }
    ) { innerPadding ->
        // content handling
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            content(modifier)
        }
    }
}
