package com.example.cookit.database

import kotlinx.coroutines.flow.Flow

class favoriteRepository(private val dao: DAO) {

    fun getAllFavorites() = dao.getFavorites()

    suspend fun insertFavorite(favorite: favorite) = dao.insertFavorite(favorite)

    suspend fun deleteFavorite(favorite: favorite) = dao.deleteFavorite(favorite)
}
