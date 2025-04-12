package com.example.geoquest.utilities.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.geoquest.ui.screens.HomeScreen
import com.example.geoquest.ui.screens.LoginScreen
import com.example.geoquest.ui.screens.RegisterScreen

@Composable
fun LogRegisterNavigator(navController: NavHostController, modifier: Modifier) {
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = "register") {

            composable(Screens.Register.route) {
                RegisterScreen(
                    modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    onLoginRedirect = { navController.navigate(Screens.Login.route) },
                    snackBarHostState = snackBarHostState
                )
            }

            composable(Screens.Login.route) {
                LoginScreen(
                    modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    onRegisterRedirect = { navController.navigate(Screens.Register.route) },
                    snackBarHostState = snackBarHostState
                )
            }

            composable(Screens.Home.route) {
                HomeScreen(modifier = modifier)
            }

//        composable(
//            route = Screens.Detail.route,
//            arguments = listOf(navArgument("id") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val id = backStackEntry.arguments?.getString("id")
//            DetailScreen(
//                onBack = { navController.popBackStack() },
//                id = id, modifier
//            )
//        }
        }
    }
}

