package com.example.cookit

import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cookit.database.favorite
import com.example.cookit.model.meal
import com.example.cookit.model.navbarItem
import com.example.cookit.screen.FavoriteScreen
import com.example.cookit.screen.MealByArea
import com.example.cookit.screen.MealByCategory
import com.example.cookit.screen.detailScreen
import com.example.cookit.screen.homeScreen
import com.example.cookit.ui.theme.CooKitTheme
import com.example.cookit.view_model.detail
import com.example.cookit.view_model.favorites
import com.example.cookit.view_model.home
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CooKitTheme {
                val navController = rememberNavController()

                val show = listOf(
                    navigtion.homeScreen.route,
                    navigtion.favoriteScreen.route
                )
                val showNavBar = navController.currentBackStackEntryAsState().value?.destination?.route
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showNavBar in show) {
                            navBar(navController)
                        }
                    }
                ) { innerPadding ->
                    appNaviagtion(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun navBar(
    navController: NavController
){
    val items = listOf(
        navbarItem("Home", Icons.Filled.Home, navigtion.homeScreen.route),
        navbarItem("Favorite", Icons.Filled.Favorite, navigtion.favoriteScreen.route)
    )

    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach {
            item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun appNaviagtion(navController : NavHostController, modifier: Modifier){
    val homeViewModel : home = viewModel()
    val detailViewModel : detail = viewModel()
    val favoriteViewModel : favorites = viewModel()

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
                    val mealName = Uri.encode(meal.mealName)
                    val mealThumb = Uri.encode(meal.mealThumb)

                    navController.navigate("detailScreen/${meal.mealId}/$mealName/$mealThumb")
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
                },
                navigateToViewAllByArea = {
                    navController.navigate(navigtion.viewAllByArea.route)
                },
                navigateToViewAllByCategory = {
                    navController.navigate(navigtion.viewAllByCategory.route)
                }
            )
        }
        composable(navigtion.detailScreen.route){
            val detailState = detailViewModel.detail.value

            val id = it.arguments?.getString("mealId")
            val mealName = it.arguments?.getString("mealName")
            val mealThumb = it.arguments?.getString("mealThumb")
            detailViewModel.fetchDetail(id.toString())
            if (id != null) {
                detailScreen(
                    navigateBack = {
                        navController.popBackStack() // untuk balik ke screen sebelumnya
                    },
                    detailState,
                    addToFavorite = {
                        val exists = favoriteViewModel.favorites.value.any { it.mealId == id }
                        if (exists) {
                            Log.d("CookItError", "Already in favorite")
                        } else {
                            favoriteViewModel.insertFavorite(favorite(null, id, mealName, mealThumb))
                        }
                    }

                )
            }
        }
        composable(navigtion.viewAllByArea.route) {
            val areaState = homeViewModel.area.value
            val mealByAreaState = homeViewModel.mealByArea.value

            MealByArea(
                areaState,
                mealByAreaState,
                navigateToDetail = {
                    meal ->
                    navController.navigate("detailScreen/${meal.mealId}")
                },
                onAreaClick = {
                    area ->
                    Log.d("CookItError", "Category clicked: $area")
                    homeViewModel.fetchMealByArea(area)
                },
                navigateToHome = {
                    navController.popBackStack()
                }
            )
        }
        composable(navigtion.viewAllByCategory.route) {
            val categoryState = homeViewModel.category.value
            val mealByCategoryState = homeViewModel.mealByCategory.value

            MealByCategory(
                categoryState,
                mealByCategoryState,
                navigateToDetail = {
                    meal ->
                    navController.navigate("detailScreen/${meal.mealId}")
                },
                onCategoryClick = {
                    category ->
                    Log.d("CookItError", "Category clicked: $category")
                    homeViewModel.fetchMealByCategory(category)
                },
                navigateToHome = {
                    navController.popBackStack()
                }
            )
        }
        composable(navigtion.favoriteScreen.route) {
            FavoriteScreen()
        }
    }
}
