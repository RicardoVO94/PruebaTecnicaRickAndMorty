package com.example.domain.usecase

import com.example.domain.model.Character
import com.example.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetCharactersUseCase(
    private val repository: CharacterRepository
) {
    operator fun invoke(
        page: Int,
        name: String? = null,
        status: String? = null,
        species: String? = null
    ): Flow<List<Character>> {
        return repository.getCharacters(page, name, status, species)
    }
}