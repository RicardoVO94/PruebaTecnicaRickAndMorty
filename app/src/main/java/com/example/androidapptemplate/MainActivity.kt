package com.example.androidapptemplate

import CharacterListScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidapptemplate.ui.detail.CharacterDetailScreen
import com.example.androidapptemplate.ui.favorites.FavoritesScreen
import com.example.androidapptemplate.ui.navigation.Navigation
import com.example.androidapptemplate.ui.theme.AndroidAppTemplateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AndroidAppTemplateTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Navigation.CharacterList.route
                ) {
                    composable(Navigation.CharacterList.route) {
                        CharacterListScreen(navController = navController)
                    }

                    composable(Navigation.CharacterDetail.route) { backStackEntry ->
                        val characterId = backStackEntry.arguments
                            ?.getString("characterId")
                            ?.toIntOrNull()

                        if (characterId != null) {
                            CharacterDetailScreen(characterId = characterId, navController = navController)
                        }
                    }
                    composable(Navigation.Favorites.route) {
                        FavoritesScreen(navController = navController)
                    }
                }
            }
        }
    }
}