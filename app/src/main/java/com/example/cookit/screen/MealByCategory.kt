package com.example.cookit.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookit.model.meal
import com.example.cookit.ui.theme.fontFamily
import com.example.cookit.view_model.home

@Composable
fun MealByCategory (
    categoryState: home.categoryState,
    mealState: home.mealState,
    navigateToDetail: (meal) -> Unit,
    onCategoryClick: (String) -> Unit,
    navigateToHome: () -> Unit
) {
    val scrollState = rememberScrollState()

    when {
        categoryState.loading && mealState.loading -> {
            ProgressIndicatorDefaults.circularColor
        }
        categoryState.error != null || mealState.error != null -> {
            Text(
                text = "ada yang error"
            )
        }
        else -> {
            Column (
                modifier = Modifier.fillMaxWidth().verticalScroll(scrollState),
            ) {
                Surface (
                    shadowElevation = 8.dp,
                    tonalElevation = 0.dp
                ) {
                    FloatingActionButton (
                        onClick = { navigateToHome() },
                        modifier = Modifier.padding(10.dp, 25.dp).height(50.dp).width(50.dp),
                        containerColor = Color.White
                    ) {
                        Icon(Icons.Filled.ArrowBack, "Small floating action button.", tint = Color.Black)
                    }
                    Column (
                        modifier = Modifier.padding(top = 35.dp, bottom = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Category",
                            fontSize = 25.sp,
                            fontFamily = fontFamily,
                            letterSpacing = (-0.5).sp
                        )
                        Spacer(Modifier.height(35.dp))
                        categoryLayout(categoryState.data, onCategoryClick)
                    }
                }
                Spacer(Modifier.height(20.dp))
                Layout(mealState.data, navigateToDetail)
            }

        }
    }
}
