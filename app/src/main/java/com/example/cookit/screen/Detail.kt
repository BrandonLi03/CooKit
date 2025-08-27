package com.example.cookit.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.sourceInformation
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.cookit.R
import com.example.cookit.model.detailMeal
import com.example.cookit.model.meal
import com.example.cookit.view_model.detail
import com.example.cookit.view_model.home

@Composable
fun detailScreen (
    navigateBack: () -> Unit,
    detailState : detail.detailState,
    addToFavorite: () -> Unit
) {

    when{
        detailState.loading -> {
            CircularProgressIndicator()
        }
        detailState.error != null -> {
            Text(
                text = "ada yang error"
            )
        }
        else -> {
            detailLayout(detailState.data, navigateBack, addToFavorite)
        }
    }
}

@Composable
fun detailLayout (
    detail: List<detailMeal>,
    navigateBack: () -> Unit,
    addToFavorite: () -> Unit
){
    LazyColumn (
        modifier = Modifier.fillMaxSize()
    ) {
        items(detail) {
            detail ->
            detailItem(detail, navigateBack, addToFavorite)
        }
    }
}

@Composable
fun detailItem (
    detail: detailMeal,
    navigateBack: () -> Unit,
    addToFavorite: () -> Unit
) {
    val context = LocalContext.current
    Box (
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.height(430.dp).fillMaxWidth(),
            painter = rememberAsyncImagePainter(
                detail.mealThumb
            ),
            contentDescription = null,
            contentScale = ContentScale.FillBounds

        )
        FloatingActionButton (
            onClick = { navigateBack() },
            modifier = Modifier.align(Alignment.TopStart).padding(20.dp).height(50.dp).width(50.dp),
            containerColor = Color.White
        ) {
            Icon(Icons.Filled.ArrowBack, "Small floating action button.", tint = Color.Black)
        }
        FloatingActionButton (
            onClick = { addToFavorite() },
            modifier = Modifier.align(Alignment.TopEnd).padding(20.dp).height(50.dp).width(50.dp),
            containerColor = Color.White
        ) {
            Icon(Icons.Filled.Favorite, "Small floating action button.", tint = Color.Black)
        }
        Column (
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).padding(top = 400.dp).clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)).background(Color.White).padding(20.dp, 40.dp, 20.dp, 40.dp)
        ) {
            Text(
                text = detail.mealName,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                fontSize = 26.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(25.dp))
            Text(
                text = "Instructions",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                fontSize = 23.sp
            )
            Spacer(Modifier.height(10.dp))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = detail.mealInstruction,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                fontSize = 17.sp
            )
            Spacer(Modifier.height(25.dp))
            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                fontSize = 23.sp
            )
            Spacer(Modifier.height(10.dp))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp
            )
            Spacer(Modifier.height(10.dp))
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = detail.mealIngredient1 + "\n" + detail.mealIngredient2
                            + "\n" + detail.mealIngredient3 + "\n" + detail.mealIngredient4
                            + "\n" + detail.mealIngredient5 + "\n" + detail.mealIngredient6
                            + "\n" + detail.mealIngredient7 + "\n" + detail.mealIngredient8
                            + "\n" + detail.mealIngredient9 + "\n" + detail.mealIngredient10
                            + "\n" + detail.mealIngredient11 + "\n" + detail.mealIngredient12
                            + "\n" + detail.mealIngredient13 + "\n" + detail.mealIngredient14,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    fontSize = 17.sp
                )
                Text(
                    text = detail.mealMeasure1 + "\n" + detail.mealMeasure2
                            + "\n" + detail.mealMeasure3 + "\n" + detail.mealMeasure4
                            + "\n" + detail.mealMeasure5 + "\n" + detail.mealMeasure6
                            + "\n" + detail.mealMeasure7 + "\n" + detail.mealMeasure8
                            + "\n" + detail.mealMeasure9 + "\n" + detail.mealMeasure10
                            + "\n" + detail.mealMeasure11 + "\n" + detail.mealMeasure12
                            + "\n" + detail.mealMeasure13 + "\n" + detail.mealMeasure14,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    fontSize = 17.sp
                )
            }
            FloatingActionButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(detail.mealVideo))
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth().padding(top = 30.dp),
                containerColor = colorResource(R.color.ruby_red),
                contentColor = colorResource(R.color.white)
            ) {
                Text(
                    text = "Watch Now",
                    fontSize = 18.sp
                )
            }
        }
    }
}
