package com.example.androidapptemplate.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetCharacterDetailUseCase
import com.example.domain.usecase.GetEpisodesForCharacterUseCase
import com.example.domain.usecase.MarkEpisodeAsWatchedUseCase
import com.example.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase,
    private val getEpisodesUseCase: GetEpisodesForCharacterUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val markEpisodeAsWatchedUseCase: MarkEpisodeAsWatchedUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CharacterDetailUiState())
    val uiState: StateFlow<CharacterDetailUiState> = _uiState

    fun onEvent(event: CharacterDetailEvent) {
        when (event) {
            is CharacterDetailEvent.LoadCharacter -> {
                loadCharacter(event.characterId)
            }
            is CharacterDetailEvent.ToggleFavorite -> {
                viewModelScope.launch {
                    toggleFavoriteUseCase(event.characterId)
                    loadCharacter(event.characterId)
                }
            }
            is CharacterDetailEvent.ToggleEpisodeWatched -> {
                viewModelScope.launch {
                    markEpisodeAsWatchedUseCase(event.episodeId)
                    _uiState.value.character?.let { loadEpisodes(it.id) }
                }
            }
        }
    }

    private fun loadCharacter(id: Int) {
        viewModelScope.launch {
            getCharacterDetailUseCase(id).onStart {
                _uiState.update { it.copy(isLoading = true) }
            }.catch {throwable ->
                _uiState.update { it.copy(isLoading = false, error = throwable.message) }
            }.collect { character ->
                _uiState.update { it.copy(character = character, isLoading = false) }
                loadEpisodes(character.id)
            }
        }
    }

    private fun loadEpisodes(characterId: Int) {
        viewModelScope.launch {
            getEpisodesUseCase(characterId).collect { episodes ->
                _uiState.update { it.copy(episodes = episodes) }
            }
        }
    }
}