package com.example.domain.usecase

import com.example.domain.model.Episode
import com.example.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetEpisodesForCharacterUseCase(
    private val repository: CharacterRepository
) {
    operator fun invoke(characterId: Int): Flow<List<Episode>> {
        return repository.getEpisodesForCharacter(characterId)
    }
}