package com.example.data.di

import com.example.domain.repository.CharacterRepository
import com.example.domain.usecase.GetCharacterDetailUseCase
import com.example.domain.usecase.GetCharactersUseCase
import com.example.domain.usecase.GetEpisodesForCharacterUseCase
import com.example.domain.usecase.MarkEpisodeAsWatchedUseCase
import com.example.domain.usecase.ToggleFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetCharacterDetailUseCase(repository: CharacterRepository): GetCharacterDetailUseCase {
        return GetCharacterDetailUseCase(repository)
    }

    @Provides
    fun provideGetEpisodesForCharacterUseCase(repository: CharacterRepository): GetEpisodesForCharacterUseCase {
        return GetEpisodesForCharacterUseCase(repository)
    }

    @Provides
    fun provideToggleFavoriteUseCase(repository: CharacterRepository): ToggleFavoriteUseCase {
        return ToggleFavoriteUseCase(repository)
    }

    @Provides
    fun provideMarkEpisodeAsWatchedUseCase(repository: CharacterRepository): MarkEpisodeAsWatchedUseCase {
        return MarkEpisodeAsWatchedUseCase(repository)
    }

    @Provides
    fun provideGetCharactersUseCase(repository: CharacterRepository): GetCharactersUseCase {
        return GetCharactersUseCase(repository)
    }
}
