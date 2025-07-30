package com.example.data.remote.mappers

import com.example.data.remote.dto.CharacterDto
import com.example.domain.model.Character
import com.example.domain.model.Location

fun CharacterDto.toDomain(): Character {
    return Character(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        image = image,
        location = Location(
            name = location.name,
            url = location.url
        ),
        episodeIds = episode.mapNotNull { url -> url.substringAfterLast("/").toIntOrNull() }
    )
}