package com.example.androidapptemplate.ui.characters

sealed class CharacterEvent {
    data class OnQueryChanged(val query: String) : CharacterEvent()
    data class OnStatusChanged(val status: String?) : CharacterEvent()
    data class OnSpeciesChanged(val species: String?) : CharacterEvent()
    data class ToggleFavorite(val characterId: Int) : CharacterEvent()
    object Search : CharacterEvent()
}