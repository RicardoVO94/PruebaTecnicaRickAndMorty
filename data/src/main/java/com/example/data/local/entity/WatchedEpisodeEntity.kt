package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watched_episodes")
data class WatchedEpisodeEntity(
    @PrimaryKey val episodeId: Int
)