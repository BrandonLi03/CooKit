package com.example.cookit

sealed class navigtion (
    val route: String
) {
    object homeScreen: navigtion("homeScreen")
    object detailScreen: navigtion("detailScreen/{mealId}/{mealName}/{mealThumb}")
    object viewAllByArea: navigtion("viewAllByArea")
    object viewAllByCategory: navigtion("viewAllByCategory")
    object splashScreen: navigtion("splashScreen")
    object favoriteScreen: navigtion("favoriteScreen")
}