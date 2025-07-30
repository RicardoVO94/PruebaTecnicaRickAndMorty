package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.FavoriteCharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: FavoriteCharacterEntity)

    @Delete
    suspend fun delete(character: FavoriteCharacterEntity)

    @Query("SELECT * FROM favorite_characters")
    fun getAll(): Flow<List<FavoriteCharacterEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_characters WHERE characterId = :id)")
    suspend fun isFavorite(id: Int): Boolean
}