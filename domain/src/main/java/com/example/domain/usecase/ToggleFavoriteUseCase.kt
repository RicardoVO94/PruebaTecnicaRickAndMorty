package com.example.domain.usecase

import com.example.domain.repository.CharacterRepository

class ToggleFavoriteUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(characterId: Int) {
        repository.toggleFavorite(characterId)
    }
}