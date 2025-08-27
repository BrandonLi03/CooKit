package com.example.cookit.screen

import android.graphics.Paint.Align
import android.graphics.drawable.shapes.Shape
import android.text.style.BackgroundColorSpan
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageSource
import com.example.cookit.R
import com.example.cookit.model.area
import com.example.cookit.model.category
import com.example.cookit.model.meal
import com.example.cookit.ui.theme.fontFamily
import com.example.cookit.view_model.home

@Composable
fun homeScreen (
    categoryState: home.categoryState,
    areaState: home.areaState,
    mealByAreaState: home.mealState,
    mealByCategoryState: home.mealState,
    navigateToDetail: (meal) -> Unit, // untuk bisa mengganti screen atau pun passing data
    onCategoryClick: (String) -> Unit, // untuk button filter
    onAreaClick: (String) -> Unit, // untuk button filter,
    navigateToViewAllByArea: () -> Unit,
    navigateToViewAllByCategory: () -> Unit
) {
    // untuk scroll
    val scrollState = rememberScrollState()

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
            Box (
                Modifier.fillMaxSize()
            ) {
                Column (
                    modifier = Modifier.padding(0.dp, 30.dp, 0.dp, 0.dp).verticalScroll(scrollState) // arah scroll
                ) {
                    Row (
                        modifier = Modifier.fillMaxWidth().padding(12.dp, 0.dp, 12.dp, 0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Welcome!",
                            fontSize = 28.sp,
                            fontFamily = fontFamily,
                            letterSpacing = (-0.5).sp
                        )
                        Image(
                            painter = painterResource(R.drawable.smaller_logo),
                            contentDescription = "",
                            modifier = Modifier.height(50.dp).width(80.dp),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    Spacer(Modifier.height(25.dp))
                    Row (
                        modifier = Modifier.fillMaxWidth().padding(12.dp, 0.dp, 12.dp, 0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "Category",
                            fontSize = 25.sp,
                            fontFamily = fontFamily,
                            letterSpacing = (-0.5).sp,
                        )
                        Text(
                            text = "View All",
                            fontSize = 17.sp,
                            fontFamily = fontFamily,
                            letterSpacing = (-0.5).sp,
                            modifier = Modifier.clickable { navigateToViewAllByCategory() },
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    categoryLayout(categories = categoryState.data, onCategoryClick)
                    Spacer(modifier = Modifier.height(20.dp))
                    mealByCategoryLayout(meals = mealByCategoryState.data, navigateToDetail)
                    Spacer(modifier = Modifier.height(30.dp))
                    Row (
                        modifier = Modifier.padding(12.dp, 0.dp, 12.dp, 0.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "Area",
                            fontSize = 25.sp,
                            fontFamily = fontFamily,
                            letterSpacing = (-0.5).sp,
                        )
                        Text(
                            text = "View All",
                            fontSize = 17.sp,
                            fontFamily = fontFamily,
                            letterSpacing = (-0.5).sp,
                            modifier = Modifier.clickable { navigateToViewAllByArea() },
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    areaLayout(areas = areaState.data, onAreaClick)
                    Spacer(modifier = Modifier.height(25.dp))
                    mealByAreaLayout(meals = mealByAreaState.data, navigateToDetail = navigateToDetail)
                }
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
        // tanpa lazy column pake for each
        meals.take(5).forEach {
            meal ->
            mealByAreaItem(meal, navigateToDetail)
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
                    fontFamily = fontFamily,
                    letterSpacing = -0.5.sp,
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
            items(meals.take(7)) {
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
                fontFamily = fontFamily,
                letterSpacing = -0.5.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(10.dp).fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun navbar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth().height(105.dp).clip(RoundedCornerShape(20.dp)).background(Color.White).padding(80.dp, 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.homeicon),
            contentDescription = "",
            modifier = Modifier.height(28.dp).width(26.dp).clickable {  },
            contentScale = ContentScale.FillBounds
        )
        Image(
            painter = painterResource(R.drawable.likeicon),
            contentDescription = "",
            modifier = Modifier.height(28.dp).width(26.dp).clickable {  },
            contentScale = ContentScale.FillBounds
        )
    }
}
