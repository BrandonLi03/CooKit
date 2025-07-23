package com.example.cookit.view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.API.APIresponse
import com.example.cookit.model.area
import com.example.cookit.model.category
import com.example.cookit.model.meal
import kotlinx.coroutines.launch

class home : ViewModel() {
    private val _categoryState = mutableStateOf(categoryState())
    val category: State<categoryState> get() = _categoryState

    private val _areaState = mutableStateOf(areaState())
    val area: State<areaState> get() = _areaState

    private val _mealByAreaState = mutableStateOf(mealState())
    val mealByArea: State<mealState> get() = _mealByAreaState

    private val _mealByCategoryState = mutableStateOf(mealState())
    val mealByCategory: State<mealState> get() = _mealByCategoryState

    init {
        fetchData()
    }

    // loading and error handling
    data class categoryState (
        val loading: Boolean = true,
        val error: String? = null,
        val data: List<category> = emptyList(),
        val clicked: Boolean = false
    )

    data class areaState (
        val loading: Boolean = true,
        val error: String? = null,
        val data: List<area> = emptyList(),
        val clicked: Boolean = false
    )

    data class mealState (
        val loading: Boolean = true,
        val error: String? = null,
        val data: List<meal> = emptyList()
    )

    fun fetchData() {
        viewModelScope.launch {
            try {
                val categoryResponse = APIresponse.getCategory()
                Log.d("CookItDebug", "Categories: ${categoryResponse.categories}")
                _categoryState.value = _categoryState.value.copy(
                    loading = false,
                    data = categoryResponse.categories,
                    error = null // ini jangan "" tapi null
                )
            } catch (e: Exception) {
                Log.e("CookItError", "Category fetch failed: ${e.message}")
                _categoryState.value = _categoryState.value.copy(
                    loading = false,
                    error = e.message ?: "Unknown Error"
                )
            }

            try {
                val areaResponse = APIresponse.getArea()
                Log.d("CookItDebug", "Areas: ${areaResponse.areas}")
                _areaState.value = _areaState.value.copy(
                    loading = false,
                    data = areaResponse.areas,
                    error = null
                )
            } catch (e: Exception) {
                Log.e("CookItError", "Area fetch failed: ${e.message}")
                _areaState.value = _areaState.value.copy(
                    loading = false,
                    error = e.message ?: "Unknown Error"
                )
            }

            try {
                val mealResponse = APIresponse.getMealbyArea("American")
                _mealByAreaState.value = _mealByAreaState.value.copy(
                    loading = false,
                    data = mealResponse.meals,
                    error = null
                )
            } catch (e: Exception) {
                Log.e("CookItError", "Area fetch failed: ${e.message}")
                _mealByAreaState.value = _mealByAreaState.value.copy(
                    loading = false,
                    error = e.message ?: "Unknown Error"
                )
            }

            try {
                val mealResponse = APIresponse.getMealbyCategory("Beef")
                _mealByCategoryState.value = _mealByCategoryState.value.copy(
                    loading = false,
                    data = mealResponse.meals,
                    error = null
                )
            } catch (e: Exception) {
                Log.e("CookItError", "Area fetch failed: ${e.message}")
                _mealByCategoryState.value = _mealByCategoryState.value.copy(
                    loading = false,
                    error = e.message ?: "Unknown Error"
                )
            }
        }
    }

    // untuk ambil data berdasarkan filter area button
    fun fetchMealByArea(areaName : String) {
        viewModelScope.launch {
            try {
                val mealResponse = APIresponse.getMealbyArea(areaName)
                _mealByAreaState.value = _mealByAreaState.value.copy(
                    data = mealResponse.meals,
                    error = null
                )
            }catch (e: Exception) {
                Log.e("CookItError", "Area fetch failed: ${e.message}")
                _mealByAreaState.value = _mealByAreaState.value.copy(
                    error = e.message ?: "Unknown Error"
                )
            }
        }
    }

    // untuk ambil data berdasarkan filter category button
    fun fetchMealByCategory(categoryName : String) {
        viewModelScope.launch {
            try {
                val mealResponse = APIresponse.getMealbyCategory(categoryName)
                _mealByCategoryState.value = _mealByCategoryState.value.copy(
                    data = mealResponse.meals,
                    error = null
                )
            }catch (e: Exception) {
                Log.e("CookItError", "Area fetch failed: ${e.message}")
                _mealByCategoryState.value = _mealByCategoryState.value.copy(
                    error = e.message ?: "Unknown Error"
                )
            }
        }
    }
}