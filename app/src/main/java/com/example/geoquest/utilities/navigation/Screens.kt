package com.example.geoquest.utilities.navigation

sealed class Screens(val route: String) {
    object Register : Screens("register")
    object Login : Screens("login")
    object Home : Screens("home")
    object Settings : Screens("settings")
    object Profile : Screens("profile")
    object Inventory : Screens("inventory")
    object Battles : Screens("battles")
    object Quests : Screens("quests")
}
