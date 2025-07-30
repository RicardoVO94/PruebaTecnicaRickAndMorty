package com.example.domain.usecase

import com.example.domain.model.Character
import com.example.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetCharacterDetailUseCase(
    private val repository: CharacterRepository
) {
    operator fun invoke(characterId: Int): Flow<Character> {
        return repository.getCharacterDetail(characterId)
    }
}