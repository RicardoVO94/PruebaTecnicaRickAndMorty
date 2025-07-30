package com.example.androidapptemplate.ui.navigation

sealed class Navigation(val route: String) {
    object CharacterList : Navigation("character_list")
    object CharacterDetail : Navigation("character_detail/{characterId}") {
        fun createRoute(id: Int) = "character_detail/$id"
    }
    object Favorites : Navigation("favorites")
}