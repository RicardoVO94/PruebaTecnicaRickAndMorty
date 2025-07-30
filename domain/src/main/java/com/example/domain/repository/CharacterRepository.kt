package com.example.domain.repository

import com.example.domain.model.Character
import com.example.domain.model.Episode
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(
        page: Int,
        name: String? = null,
        status: String? = null,
        species: String? = null
    ): Flow<List<Character>>
    fun getCharacterDetail(characterId: Int): Flow<Character>

    fun getEpisodesForCharacter(characterId: Int): Flow<List<Episode>>

    suspend fun toggleFavorite(characterId: Int)

    suspend fun markEpisodeAsWatched(episodeId: Int)
}