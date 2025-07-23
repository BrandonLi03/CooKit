package com.example.cookit

sealed class navigtion (
    val route: String
) {
    object homeScreen: navigtion("homeScreen")
    object detailScreen: navigtion("detailScreen/{mealId}")
}