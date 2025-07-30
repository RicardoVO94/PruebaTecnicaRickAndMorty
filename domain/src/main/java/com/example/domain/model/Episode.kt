package com.example.domain.model

data class Episode(
    val id: Int,
    val name: String,
    val episodeCode: String,
    val isWatched: Boolean = false
)