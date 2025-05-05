package com.example.geoquest.ui.components.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.geoquest.ui.components.baseComponents.CustomBottomBar
import com.example.geoquest.ui.components.baseComponents.CustomTopAppBar
import com.example.geoquest.ui.viewModels.factories.GlobalViewModels

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Helmet(
    modifier: Modifier = Modifier,
    navigator: NavHostController,
    content: @Composable (Modifier) -> Unit,
) {
    val viewModel = GlobalViewModels.navBarViewModel
    val shouldAnimate = viewModel.shouldAnimate.collectAsState()
    val iconSize = 60.dp

    Scaffold(
        modifier = modifier,
        topBar = {
            if (shouldAnimate.value) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp)
                )
                LaunchedEffect(Unit) {
                    // Reset dopo l'animazione se serve
                    viewModel.resetAnimation()
                }
            }else{
                CustomTopAppBar(
                    iconSize = iconSize,
                    navigator = navigator
                )
            }
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
