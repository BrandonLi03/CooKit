package com.example.cookit.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.cookit.R
import com.example.cookit.database.favorite
import com.example.cookit.ui.theme.fontFamily
import com.example.cookit.view_model.favorites

@Composable
fun FavoriteScreen(
    viewModel: favorites = viewModel()
) {
    val favorites by viewModel.favorites.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(top = 30.dp, bottom = 16.dp)
    ) {
        // Header
        item {
            Text(
                text = "Favorite",
                fontSize = 28.sp,
                fontFamily = fontFamily,
                letterSpacing = (-0.5).sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Loading state
        if (isLoading) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        } else if (favorites.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No favorites yet",
                        fontSize = 16.sp,
                        fontFamily = fontFamily
                    )
                }
            }
        } else {
            items(favorites) { favorite ->
                FavoriteItem(
                    favorite = favorite,
                    onDeleteClick = { viewModel.deleteFavorite(it) }
                )
            }
        }
    }
}

@Composable
fun FavoriteItem(
    favorite: favorite,
    onDeleteClick: (favorite) -> Unit
) {
    Card(
        modifier = Modifier.clickable {  }.fillMaxWidth().padding(10.dp, 5.dp).wrapContentHeight(),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
    ) {
        Row {
            Image(
                painter = rememberAsyncImagePainter(favorite.mealThumb),
                modifier = Modifier.height(100.dp).width(110.dp).padding(10.dp).clip(shape = RoundedCornerShape(15.dp)),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Spacer(Modifier.width(15.dp))
            Column (
                modifier = Modifier.padding(0.dp, 12.dp, 5.dp, 10.dp)
            ){
                Text(
                    text = favorite.mealName?: "",
                    fontSize = 19.sp,
                    fontFamily = fontFamily,
                    letterSpacing = -0.5.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Meal Id : " + favorite.mealId,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                )
                IconButton(
                        onClick = { onDeleteClick(favorite) }
                        ) {
                    Text("🗑️") // You can replace this with proper icon
                }
            }
        }
    }
}
