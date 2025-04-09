package com.example.geoquest.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.geoquest.ui.theme.getGradient

@Composable
fun AppNavHost(navController: NavHostController,modifier: Modifier) {

    NavHost(navController = navController, startDestination = "home") {

        composable(Screens.Home.route) {
            HomeScreen(onNavigateToDetail = { id ->
                navController.navigate(Screens.Detail.createRoute(id))
            },modifier)
        }

        composable(
            route = Screens.Detail.route,
            arguments = listOf(navArgument ("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            DetailScreen(
                onBack = {navController.popBackStack()},
                id = id,modifier)
        }
    }
}

@Composable
fun HomeScreen(onNavigateToDetail: (String) -> Unit,modifier: Modifier) {
    Column(modifier = modifier.fillMaxSize().background(getGradient()), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Home Screen")
        Button(onClick = { onNavigateToDetail("IDBABY") }) {
            Text("Vai a Dettaglio")
        }
    }
}

@Composable
fun DetailScreen(onBack: () -> Unit, id: String?, modifier: Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Detail Screen, my man passed up this shit right here => $id")
        Button(onClick = onBack) {
            Text("Torna indietro")
        }
    }
}

