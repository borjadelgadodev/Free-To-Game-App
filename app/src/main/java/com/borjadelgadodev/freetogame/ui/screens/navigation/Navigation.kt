package com.borjadelgadodev.freetogame.ui.screens.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.borjadelgadodev.freetogame.ui.screens.detail.DetailScreen
import com.borjadelgadodev.freetogame.ui.screens.home.HomeScreen
import com.borjadelgadodev.freetogame.ui.screens.navigation.ScreenDestination.Constants.NavArgs
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun Navigation() {
    val navController = rememberNavController()

    Scaffold(bottomBar = { BottomNavigationBar(navController) }) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = ScreenDestination.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(ScreenDestination.Home.route) {
                HomeScreen(
                    onClick = { game ->
                        navController.navigate(ScreenDestination.Detail.createDetailRoute(game.id))
                    }, viewModel = koinViewModel()
                )
            }
            composable(ScreenDestination.MyGames.route) {
                // Implementacion mas adelante
            }
            composable(ScreenDestination.MyProfile.route) {
                // Implementacion mas adelante
            }
            composable(
                route = ScreenDestination.Detail.route,
                arguments = listOf(navArgument(NavArgs.GameId.key) { type = NavType.IntType })
            ) { backStackEntry ->
                val gameId = requireNotNull(backStackEntry.arguments?.getInt(NavArgs.GameId.key))
                DetailScreen(
                    vm = koinViewModel { parametersOf(gameId) },
                    onBackClick = { navController.popBackStack() })
            }
        }
    }
}
