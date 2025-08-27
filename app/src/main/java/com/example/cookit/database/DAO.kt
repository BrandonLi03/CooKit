package com.example.cookit.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    // ada banyak strategy, kalau ini supaya kalau ada conflict data yang di insert akan di ignore
    fun insertFavorite(favorite: favorite)

    @Query("select * from favorites")
    fun getFavorites(): Flow<List<favorite>>

    @Delete
    fun deleteFavorite(favorite: favorite)
}