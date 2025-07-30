package com.example.androidapptemplate.ui.detail

import com.example.domain.model.Character
import com.example.domain.model.Episode

data class CharacterDetailUiState(
    val character: Character? = null,
    val episodes: List<Episode> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
