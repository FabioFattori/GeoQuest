package com.example.geoquest.navigation

sealed class Screens(val route : String) {
    object Home : Screens("home")
    object Detail : Screens("detail/{id}") {
        fun createRoute(id: String) = "detail/$id"
    }
}
