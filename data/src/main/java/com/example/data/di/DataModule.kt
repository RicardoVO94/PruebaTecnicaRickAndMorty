package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.dao.AppDatabase
import com.example.data.local.dao.FavoriteCharacterDao
import com.example.data.local.dao.WatchedEpisodeDao
import com.example.data.remote.api.RickMortyApi
import com.example.data.repository.CharacterRepositoryImpl
import com.example.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideApi(): RickMortyApi {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RickMortyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "rickmorty.db"
            ).fallbackToDestructiveMigration(false).build()
    }

    @Provides
    fun provideFavoriteDao(db: AppDatabase): FavoriteCharacterDao = db.favoriteCharacterDao()

    @Provides
    fun provideWatchedDao(db: AppDatabase): WatchedEpisodeDao = db.watchedEpisodeDao()

    @Provides
    @Singleton
    fun provideCharacterRepository(
        api: RickMortyApi,
        favoriteDao: FavoriteCharacterDao,
        watchedDao: WatchedEpisodeDao
    ): CharacterRepository = CharacterRepositoryImpl(api, favoriteDao, watchedDao)
}