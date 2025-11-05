package com.boxbox.f1app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.boxbox.f1app.presentation.home.HomeScreen
import com.boxbox.f1app.presentation.racedetail.RaceDetailScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object RaceDetail : Screen("race_detail/{raceId}") {
        fun createRoute(raceId: String) = "race_detail/$raceId"
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToRaceDetail = { raceId ->
                    navController.navigate(Screen.RaceDetail.createRoute(raceId))
                }
            )
        }

        composable(
            route = Screen.RaceDetail.route,
            arguments = listOf(
                navArgument("raceId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val raceId = backStackEntry.arguments?.getString("raceId") ?: return@composable
            RaceDetailScreen(
                raceId = raceId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}