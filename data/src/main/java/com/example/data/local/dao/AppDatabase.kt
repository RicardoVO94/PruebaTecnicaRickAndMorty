package com.example.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.entity.FavoriteCharacterEntity
import com.example.data.local.entity.WatchedEpisodeEntity

@Database(
    entities = [FavoriteCharacterEntity::class, WatchedEpisodeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteCharacterDao(): FavoriteCharacterDao
    abstract fun watchedEpisodeDao(): WatchedEpisodeDao
}