package com.example.data.remote.api

import com.example.data.remote.dto.CharacterDto
import com.example.data.remote.dto.CharacterResponseDto
import com.example.data.remote.dto.EpisodeDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickMortyApi {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null
    ): CharacterResponseDto
    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): EpisodeDto
    @GET("character/")
    suspend fun searchCharacters(@Query("name") name: String): List<EpisodeDto>
}