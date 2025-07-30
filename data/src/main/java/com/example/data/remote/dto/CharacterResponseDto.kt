package com.example.data.remote.dto

data class CharacterResponseDto(
    val info: PageInfoDto,
    val results: List<CharacterDto>
)