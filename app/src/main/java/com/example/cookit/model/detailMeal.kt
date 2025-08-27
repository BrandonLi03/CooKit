package com.example.cookit.model

import com.google.gson.annotations.SerializedName

data class detailMeal (
    @SerializedName("idMeal")
    val mealId: String,

    @SerializedName("strMeal")
    val mealName: String,

    @SerializedName("strMealThumb")
    val mealThumb: String,
    @SerializedName("strInstructions")
    val mealInstruction: String,

    @SerializedName("strIngredient1")
    val mealIngredient1: String,

    @SerializedName("strIngredient2")
    val mealIngredient2: String,
    @SerializedName("strIngredient3")
    val mealIngredient3: String,
    @SerializedName("strIngredient4")
    val mealIngredient4: String,
    @SerializedName("strIngredient5")
    val mealIngredient5: String,
    @SerializedName("strIngredient6")
    val mealIngredient6: String,
    @SerializedName("strIngredient7")
    val mealIngredient7: String,
    @SerializedName("strIngredient8")
    val mealIngredient8: String,
    @SerializedName("strIngredient9")
    val mealIngredient9: String,
    @SerializedName("strIngredient10")
    val mealIngredient10: String,
    @SerializedName("strIngredient11")
    val mealIngredient11: String,
    @SerializedName("strIngredient12")
    val mealIngredient12: String,
    @SerializedName("strIngredient13")
    val mealIngredient13: String,
    @SerializedName("strIngredient14")
    val mealIngredient14: String,
    @SerializedName("strIngredient15")
    val mealIngredient15: String,
    @SerializedName("strIngredient16")
    val mealIngredient16: String,
    @SerializedName("strIngredient17")
    val mealIngredient17: String,
    @SerializedName("strIngredient18")
    val mealIngredient18: String,
    @SerializedName("strIngredient19")
    val mealIngredient19: String,
    @SerializedName("strIngredient20")
    val mealIngredient20: String,

    @SerializedName("strMeasure1")
    val mealMeasure1: String,
    @SerializedName("strMeasure2")
    val mealMeasure2: String,
    @SerializedName("strMeasure3")
    val mealMeasure3: String,
    @SerializedName("strMeasure4")
    val mealMeasure4: String,
    @SerializedName("strMeasure5")
    val mealMeasure5: String,
    @SerializedName("strMeasure6")
    val mealMeasure6: String,
    @SerializedName("strMeasure7")
    val mealMeasure7: String,
    @SerializedName("strMeasure8")
    val mealMeasure8: String,
    @SerializedName("strMeasure9")
    val mealMeasure9: String,
    @SerializedName("strMeasure10")
    val mealMeasure10: String,
    @SerializedName("strMeasure11")
    val mealMeasure11: String,
    @SerializedName("strMeasure12")
    val mealMeasure12: String,
    @SerializedName("strMeasure13")
    val mealMeasure13: String,
    @SerializedName("strMeasure14")
    val mealMeasure14: String,
    @SerializedName("strMeasure15")
    val mealMeasure15: String,
    @SerializedName("strMeasure16")
    val mealMeasure16: String,
    @SerializedName("strMeasure17")
    val mealMeasure17: String,
    @SerializedName("strMeasure18")
    val mealMeasure18: String,
    @SerializedName("strMeasure19")
    val mealMeasure19: String,
    @SerializedName("strMeasure20")
    val mealMeasure20: String,

    @SerializedName("strYoutube")
    val mealVideo: String,

//    val isfavorit: Boolean = false
)

data class detailMealResponse (
    val meals: List<detailMeal>
)