package com.example.geoquest.utilities.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.geoquest.ui.screens.BattleScreen
import com.example.geoquest.ui.screens.HomeScreen
import com.example.geoquest.ui.screens.InventoryScreen
import com.example.geoquest.ui.screens.ProfileScreen
import com.example.geoquest.ui.screens.SettingsScreen

@Composable
fun GameNavigator(navController: NavHostController, modifier: Modifier) {

    NavHost(navController = navController, startDestination = "home") {

        composable(Screens.Home.route) {
            HomeScreen(modifier = modifier)
        }

        composable(Screens.Profile.route) {
            ProfileScreen(modifier = modifier)
        }

        composable(Screens.Settings.route) {
            SettingsScreen(modifier = modifier)
        }

        composable(Screens.Battles.route) {
            BattleScreen(modifier = modifier)
        }

        composable(Screens.Inventory.route) {
            InventoryScreen(modifier = modifier)
        }
    }
}

