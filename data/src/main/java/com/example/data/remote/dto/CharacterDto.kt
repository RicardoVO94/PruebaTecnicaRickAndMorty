package com.example.data.remote.dto

data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String,
    val location: LocationDto,
    val episode: List<String>
)