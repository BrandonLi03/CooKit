package com.example.cookit.model

import com.google.gson.annotations.SerializedName

data class category (
    @SerializedName("strCategory")
    val categoryName: String,

    var isClicked: Boolean = false
)

data class categoryResponse (
    @SerializedName("meals")
    val categories: List<category>
)