package com.example.androidapptemplate.ui.characters

import com.example.domain.model.Character

data class CharacterUiState(
    val characters: List<Character> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val query: String = "",
    val status: String? = null,
    val species: String? = null
)