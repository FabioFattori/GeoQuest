package com.example.geoquest.utilities.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.geoquest.ui.screens.HomeScreen
import com.example.geoquest.ui.screens.InventoryScreen
import com.example.geoquest.ui.screens.LeagueScreen
import com.example.geoquest.ui.screens.ProfileScreen
import com.example.geoquest.ui.screens.QuestScreen
import com.example.geoquest.ui.screens.SettingsScreen

@Composable
fun GameNavigator(
    navController: NavHostController,
    modifier: Modifier,
    isDark: MutableState<Boolean>,
    currentLanguage: MutableState<String>
) {

    NavHost(navController = navController, startDestination = "home") {


        composable(Screens.Home.route)
        {
            HomeScreen(modifier = modifier, goToQuests = {
                navController.navigate(Screens.Quests.route)
            })
        }

        composable(Screens.Profile.route) {
            ProfileScreen(modifier = modifier)
        }

        composable(Screens.Settings.route) {
            SettingsScreen(
                modifier = modifier,
                isDark = isDark,
                currentLanguage = currentLanguage
            )
        }

        composable(Screens.League.route) {
            LeagueScreen(modifier = modifier)
        }

        composable(Screens.Inventory.route) {
            InventoryScreen(modifier = modifier, reloadPage = {
                navController.navigate(Screens.Inventory.route)
            })
        }

        composable(Screens.Quests.route) {
            QuestScreen(modifier = modifier)
        }
    }
}

