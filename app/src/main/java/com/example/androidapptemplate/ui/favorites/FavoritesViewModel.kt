package com.example.androidapptemplate.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetCharactersUseCase
import com.example.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch {
            getCharactersUseCase(page = 1).collect { allCharacters ->
                val favorites = allCharacters.filter { it.isFavorite }
                _uiState.update { it.copy(favorites = favorites) }
            }
        }
    }

    fun toggleFavorite(characterId: Int) {
        viewModelScope.launch {
            toggleFavoriteUseCase(characterId)
            loadFavorites()
        }
    }
}
