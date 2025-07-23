package com.example.cookit.model

import com.google.gson.annotations.SerializedName

data class area (
    @SerializedName("strArea")
    val areaName: String,

    var isClicked: Boolean = false
)

data class areaResponse (
    @SerializedName("meals")
    val areas: List<area>
)