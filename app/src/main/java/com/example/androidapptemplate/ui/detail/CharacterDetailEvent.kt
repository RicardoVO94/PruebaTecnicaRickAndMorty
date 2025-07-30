package com.example.androidapptemplate.ui.detail

sealed class CharacterDetailEvent {
    data class LoadCharacter(val characterId: Int) : CharacterDetailEvent()
    data class ToggleFavorite(val characterId: Int) : CharacterDetailEvent()
    data class ToggleEpisodeWatched(val episodeId: Int) : CharacterDetailEvent()
}