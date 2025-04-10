package com.example.geoquest.navigation

sealed class Screens(val route : String) {
    object Register : Screens("register")
    object Login : Screens("login")
    object Home : Screens("home")
    object Detail : Screens("detail/{id}") {
        fun createRoute(id: String) = "detail/$id"
    }
}
