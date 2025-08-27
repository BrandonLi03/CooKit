package com.example.cookit.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.database.database
import com.example.cookit.database.favoriteRepository
import com.example.cookit.database.favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class favorites(application: Application) : AndroidViewModel(application) {

    private val favoriteRepository: favoriteRepository

    init {
        val dao = database.getDatabase(application).dao()
        favoriteRepository = favoriteRepository(dao)
        loadFavorites()
    }

    private val _favorites = MutableStateFlow<List<favorite>>(emptyList())
    val favorites: StateFlow<List<favorite>> = _favorites.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private fun loadFavorites() {
        viewModelScope.launch {
            favoriteRepository.getAllFavorites().collect { favoriteList ->
                _favorites.value = favoriteList
            }
        }
    }

    fun insertFavorite(favorite: favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            favoriteRepository.insertFavorite(favorite)
            _isLoading.value = false
        }
    }

    fun deleteFavorite(favorite: favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            favoriteRepository.deleteFavorite(favorite)
            _isLoading.value = false
        }
    }
}
