package com.example.data.repository

import com.example.data.local.dao.FavoriteCharacterDao
import com.example.data.local.dao.WatchedEpisodeDao
import com.example.data.local.entity.FavoriteCharacterEntity
import com.example.data.local.entity.WatchedEpisodeEntity
import com.example.data.remote.api.RickMortyApi
import com.example.data.remote.mappers.toDomain
import com.example.domain.model.Character
import com.example.domain.model.Episode
import com.example.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class CharacterRepositoryImpl(
    private val api: RickMortyApi,
    private val favoriteCharacterDao: FavoriteCharacterDao,
    private val watchedEpisodeDao: WatchedEpisodeDao
): CharacterRepository {
    override fun getCharacters(
        page: Int,
        name: String?,
        status: String?,
        species: String?
    ): Flow<List<Character>> =  flow {
        val response = api.getCharacters(page, name, status, species)
        val favorites = favoriteCharacterDao.getAll().first().map { it.characterId }.toSet()
        val characters = response.results.map { dto ->
            dto.toDomain().copy(
                isFavorite = favorites.contains(dto.id)
            )
        }
        emit(characters)
    }

    override fun getCharacterDetail(characterId: Int): Flow<Character> = flow {
        val response = api.getCharacters(1)
        val character = response.results.first { it.id == characterId }
        val isFav = favoriteCharacterDao.isFavorite(characterId)
        emit(character.toDomain().copy(isFavorite = isFav))
    }

    override fun getEpisodesForCharacter(characterId: Int): Flow<List<Episode>> = flow {
        val character = api.getCharacters(1).results.first { it.id == characterId }
        val watchedIds = watchedEpisodeDao.getAll().first().map { it.episodeId }.toSet()
        val episodes = character.episode.mapNotNull { url ->
            val id = url.substringAfterLast("/").toIntOrNull()
            id?.let {
                Episode(
                    id = it,
                    name = "Episode $it",
                    episodeCode = "S01E${it.toString().padStart(2, '0')}",
                    isWatched = watchedIds.contains(it)
                )
            }
        }
        emit(episodes)
    }

    override suspend fun toggleFavorite(characterId: Int) {
        if (favoriteCharacterDao.isFavorite(characterId)) {
            favoriteCharacterDao.delete(FavoriteCharacterEntity(characterId))
        } else {
            favoriteCharacterDao.insert(FavoriteCharacterEntity(characterId))
        }
    }

    override suspend fun markEpisodeAsWatched(episodeId: Int) {
        watchedEpisodeDao.insert(WatchedEpisodeEntity(episodeId))
    }
}