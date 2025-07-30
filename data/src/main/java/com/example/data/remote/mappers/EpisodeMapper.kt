package com.example.data.remote.mappers

import com.example.data.remote.dto.EpisodeDto
import com.example.domain.model.Episode

fun EpisodeDto.toDomain(isWatched: Boolean): Episode {
    return Episode(
        id = id,
        name = name,
        episodeCode = episode,
        isWatched = isWatched
    )
}