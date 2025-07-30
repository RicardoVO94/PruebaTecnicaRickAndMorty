package com.example.androidapptemplate.ui.favorites

import com.example.domain.model.Character

data class FavoritesUiState(
    val favorites: List<Character> = emptyList(),
    val isLoading: Boolean = false
)