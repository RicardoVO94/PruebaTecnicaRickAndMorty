package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.WatchedEpisodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchedEpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(episode: WatchedEpisodeEntity)

    @Query("SELECT * FROM watched_episodes")
    fun getAll(): Flow<List<WatchedEpisodeEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM watched_episodes WHERE episodeId = :id)")
    suspend fun isWatched(id: Int): Boolean
}