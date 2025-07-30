package com.example.androidapptemplate.ui.navigation

import CharacterListScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidapptemplate.ui.detail.CharacterDetailScreen
import com.example.androidapptemplate.ui.favorites.FavoritesScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Navigation.CharacterList
    ) {
        composable(Navigation.CharacterList.toString()) {
            CharacterListScreen(navController = navController)
        }

        composable("${Navigation.CharacterDetail}/{characterId}") { backStackEntry ->
            val characterId = backStackEntry.arguments?.getString("characterId")?.toIntOrNull() ?: return@composable
            CharacterDetailScreen(characterId = characterId, navController = navController)
        }

        composable(Navigation.Favorites.toString()) {
            FavoritesScreen(navController = navController)
        }
    }
}