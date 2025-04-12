package com.example.geoquest.utilities.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.geoquest.ui.screens.HomeScreen

@Composable
fun GameNavigator(navController: NavHostController, modifier: Modifier) {

    NavHost(navController = navController, startDestination = "home") {

        composable(Screens.Home.route) {
            HomeScreen(modifier = modifier)
        }
    }
}

