package com.example.cookit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cookit.model.meal
import com.example.cookit.screen.detailScreen
import com.example.cookit.screen.homeScreen
import com.example.cookit.ui.theme.CooKitTheme
import com.example.cookit.view_model.detail
import com.example.cookit.view_model.home

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CooKitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    appNaviagtion(
                        navController = rememberNavController(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun appNaviagtion(navController : NavHostController, modifier: Modifier){
    val homeViewModel : home = viewModel()
    val detailViewModel : detail = viewModel()

    NavHost(navController = navController, startDestination = navigtion.homeScreen.route, modifier = Modifier){
        composable(navigtion.homeScreen.route){
            val categoryState = homeViewModel.category.value
            val areaState = homeViewModel.area.value
            val mealByAreaState = homeViewModel.mealByArea.value
            val mealByCategoryState = homeViewModel.mealByCategory.value

            homeScreen(
                categoryState,
                areaState,
                mealByAreaState,
                mealByCategoryState,
                navigateToDetail = {
                    meal->
                    navController.navigate("DetailScreen/${meal.mealId}")

                },
                onCategoryClick = {
                    category ->
                    Log.d("CookItError", "Category clicked: $category")
                    homeViewModel.fetchMealByCategory(category)
                },
                onAreaClick = {
                    area ->
                    Log.d("CookItError", "Category clicked: $area")
                    homeViewModel.fetchMealByArea(area)
                }
            )
        }
        composable(navigtion.detailScreen.route){
            val detailState = detailViewModel.detail.value

            val id = it.arguments?.getString("mealId")
            detailViewModel.fetchDetail(id.toString())
            if (id != null) {
                detailScreen(
                    navigateBack = {
                        navController.popBackStack() // untuk balik ke screen sebelumnya
                    },
                    detailState
                )
            }
        }
    }
}