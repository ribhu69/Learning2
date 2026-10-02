package com.example.learning.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.learning.ui.screens.HomeScreen
import com.example.learning.ui.screens.SavedJokesScreen
import com.example.learning.viewmodel.JokeViewModel

private object Routes {
    const val HOME = "home"
    const val SAVED = "saved"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val jokeViewModel: JokeViewModel = viewModel()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(
                viewModel = jokeViewModel,
                onNavigateToSaved = { navController.navigate(Routes.SAVED) }
            )
        }
        composable(Routes.SAVED) {
            SavedJokesScreen(
                viewModel = jokeViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
