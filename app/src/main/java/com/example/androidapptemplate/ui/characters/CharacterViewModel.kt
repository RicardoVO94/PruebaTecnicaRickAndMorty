package com.example.androidapptemplate.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetCharactersUseCase
import com.example.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
open class CharacterViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CharacterUiState())
    val uiState: StateFlow<CharacterUiState> = _uiState

    init {
        getCharacters()
    }

    fun onEvent(event: CharacterEvent) {
        when (event) {
            is CharacterEvent.OnQueryChanged -> {
                _uiState.update { it.copy(query = event.query) }
                getCharacters()
            }
            is CharacterEvent.OnStatusChanged -> {
                _uiState.update { it.copy(status = event.status) }
                getCharacters()
            }
            is CharacterEvent.OnSpeciesChanged -> {
                _uiState.update { it.copy(species = event.species) }
                getCharacters()
            }
            is CharacterEvent.Search -> {
                getCharacters()
            }
            is CharacterEvent.ToggleFavorite -> {
                viewModelScope.launch {
                    toggleFavoriteUseCase(event.characterId)
                    getCharacters()
                }
            }
        }
    }

    private fun getCharacters() {
        viewModelScope.launch {
            getCharactersUseCase(
                page = 1,
                name = _uiState.value.query,
                status = _uiState.value.status,
                species = _uiState.value.species
            ).onStart {
                _uiState.update { it.copy(isLoading = true, error = null) }
            }.catch { e ->
                val errorMessage = when (e) {
                    is HttpException -> {
                        if (e.code() == 404) {
                            // No hay resultados, lista vacía, sin error.
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    characters = emptyList(),
                                    error = null
                                )
                            }
                            return@catch
                        } else {
                            "Error del servidor (${e.code()})"
                        }
                    }
                    is IOException -> "Error de conexión. Verifica tu red."
                    else -> "Error inesperado: ${e.localizedMessage}"
                }
                    _uiState.update {
                    it.copy(isLoading = false, error = errorMessage)
                }
            }.collect { characters ->
                _uiState.update { it.copy(isLoading = false, characters = characters) }
            }
        }
    }

}