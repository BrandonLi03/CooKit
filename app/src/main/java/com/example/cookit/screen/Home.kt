package com.example.cookit.screen

import android.graphics.Paint.Align
import android.graphics.drawable.shapes.Shape
import android.text.style.BackgroundColorSpan
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.cookit.R
import com.example.cookit.model.area
import com.example.cookit.model.category
import com.example.cookit.model.meal
import com.example.cookit.view_model.home

@Composable
fun homeScreen (
    categoryState: home.categoryState,
    areaState: home.areaState,
    mealByAreaState: home.mealState,
    mealByCategoryState: home.mealState,
    navigateToDetail: (meal) -> Unit, // untuk bisa mengganti screen atau pun passing data
    onCategoryClick: (String) -> Unit, // untuk button filter
    onAreaClick: (String) -> Unit // untuk button filter
) {

    when {
        categoryState.loading && areaState.loading -> {
            // kalau lagi loading muncul loading icon / progress icon
            CircularProgressIndicator()
        }
        categoryState.error != null || areaState.error != null -> {
            // kalau ada error maka muncul text error
            Text(
                text = "ada yang error"
            )
        }
        else -> {
            // kalau aman muncul screen
            Column (
                modifier = Modifier.padding(0.dp, 20.dp, 0.dp, 0.dp)
            ) {
                // navbar
                // username and logo
                Text(
                    text = "Category",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(11.dp, 0.dp, 0.dp, 0.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                categoryLayout(categories = categoryState.data, onCategoryClick)
                Spacer(modifier = Modifier.height(20.dp))
                mealByCategoryLayout(meals = mealByCategoryState.data, navigateToDetail)
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Area",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(11.dp, 0.dp, 0.dp, 0.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                areaLayout(areas = areaState.data, onAreaClick)
                Spacer(modifier = Modifier.height(20.dp))
                mealByAreaLayout(meals = mealByAreaState.data, navigateToDetail = navigateToDetail)
            }
        }
    }
}

@Composable
fun categoryLayout (
    categories: List<category>,
    onCategoryClick: (String) -> Unit
) {
    Column {
        LazyRow {
            items(categories){
                category ->
                categoryItem(category = category, onClick = onCategoryClick)
            }
        }
    }
}

@Composable
fun categoryItem (
    category: category,
    onClick: (String) -> Unit
) {
    FloatingActionButton(
        modifier = Modifier.height(40.dp).wrapContentWidth().padding(10.dp, 0.dp),
        onClick = {onClick(category.categoryName)},
        containerColor = colorResource(id = R.color.eagle),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(
            text = category.categoryName,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white),
            modifier = Modifier.padding(15.dp, 5.dp)
        )
    }
}

@Composable
fun areaLayout (
    areas: List<area>,
    onAreaClick: (String) -> Unit
) {
    Column {
        LazyRow {
            items(areas){
                area ->
                areaItem(area = area, onAreaClick)
            }
        }
    }
}

@Composable
fun areaItem (
    area: area,
    onClick: (String) -> Unit
) {
    FloatingActionButton(
        modifier = Modifier.height(40.dp).wrapContentWidth().padding(10.dp, 0.dp),
        onClick = {onClick(area.areaName)},
        containerColor = colorResource(id = R.color.eagle),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(
            text = area.areaName,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white),
            modifier = Modifier.padding(15.dp, 5.dp)
        )
    }
}

@Composable
fun mealByAreaLayout (
    meals: List<meal>,
    navigateToDetail: (meal) -> Unit
) {
    Column {
        LazyColumn {
            items(meals) {
                meal ->
                mealByAreaItem(meal, navigateToDetail)
            }
        }
    }
}

@Composable
fun mealByAreaItem (
    meal : meal,
    navigateToDetail: (meal) -> Unit
) {
    Card(
        modifier = Modifier.clickable { navigateToDetail(meal) }.fillMaxWidth().padding(10.dp, 5.dp).wrapContentHeight(),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
    ) {
        Row {
            Image(
                painter = rememberAsyncImagePainter(meal.mealThumb),
                modifier = Modifier.height(100.dp).width(110.dp).padding(10.dp).clip(shape = RoundedCornerShape(15.dp)),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Spacer(Modifier.width(15.dp))
            Column (
                modifier = Modifier.padding(0.dp, 12.dp, 5.dp, 10.dp)
            ){
                Text(
                    text = meal.mealName,
                    fontSize = 19.sp,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Meal Id : " + meal.mealId,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                )
            }
        }
    }
}

@Composable
fun mealByCategoryLayout (
    meals: List<meal>,
    navigateToDetail: (meal) -> Unit
) {
    Column {
        LazyRow {
            items(meals) {
                    meal ->
                mealByCategoryItem(meal, navigateToDetail)
            }
        }
    }
}

@Composable
fun mealByCategoryItem (
    meal : meal,
    navigateToDetail: (meal) -> Unit
){
    Card (
        modifier = Modifier.clickable { navigateToDetail(meal) }.padding(10.dp, 10.dp).wrapContentHeight().width(145.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(meal.mealThumb),
                modifier = Modifier.height(140.dp).width(145.dp).padding(10.dp).clip(shape = RoundedCornerShape(15.dp)),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = meal.mealName,
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(10.dp).fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}