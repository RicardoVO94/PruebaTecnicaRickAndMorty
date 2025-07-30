package com.example.domain.usecase

import com.example.domain.repository.CharacterRepository

class MarkEpisodeAsWatchedUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(espisodeId: Int) {
        repository.markEpisodeAsWatched(espisodeId)
    }
}