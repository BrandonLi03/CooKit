package com.example.cookit.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class meal (
    @SerializedName("strMeal")
    val mealName: String,

    @SerializedName("strMealThumb")
    val mealThumb: String,

    @SerializedName("idMeal")
    val mealId: String
)

data class mealResponse (
    val meals: List<meal>
)