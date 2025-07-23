package com.example.cookit.view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.API.APIresponse
import com.example.cookit.model.detailMeal
import com.example.cookit.model.meal
import kotlinx.coroutines.launch

class detail : ViewModel(){
    private val _detailState = mutableStateOf(detailState())

    val detail: State<detailState> get() = _detailState

    // error handling
    data class detailState(
        val loading: Boolean = true,
        val error: String? = null,
        val data: List<detailMeal> = emptyList()
    )

    fun fetchDetail(mealId: String) {
        viewModelScope.launch {
            try {
                val detailResponse = APIresponse.getDetailMeal(mealId)
                _detailState.value = _detailState.value.copy(
                    loading = false,
                    data = detailResponse.meals,
                    error = null
                )
            }catch (e: Exception) {
                Log.e("CookItError", "Category fetch failed: ${e.message}")
                _detailState.value = _detailState.value.copy(
                    loading = false,
                    error = e.message ?: "Unknown Error"
                )
            }
        }
    }
}