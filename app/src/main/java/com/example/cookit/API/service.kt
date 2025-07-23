package com.example.cookit.API

import com.example.cookit.model.areaResponse
import com.example.cookit.model.categoryResponse
import com.example.cookit.model.detailMeal
import com.example.cookit.model.detailMealResponse
import com.example.cookit.model.mealResponse
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

val baseUrl = "https://www.themealdb.com/api/json/v1/1/"

val APIresponse = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
    .build().create(service::class.java)

interface service {
    // untuk retrieve data category pakai GET, di dalam () adalah endpoint dari API
    @GET("list.php")
    suspend fun getCategory(@Query("c") c: String = "list") : categoryResponse

    // untuk retrieve data area pakai GET, di dalam () adalah endpoint dari API
    @GET("list.php")
    suspend fun getArea(@Query("a") a: String = "list") : areaResponse
    // suspend disini termasuk coroutines, supaya aplikasi tidak freeze saat menunggu respon dari API

    // untuk retrieve data meal berdasarkan area pakai GET, di dalam () adalah endpoint dari API
    @GET("filter.php")
    suspend fun getMealbyArea(@Query("a") a: String) : mealResponse

    // untuk retrieve data meal berdasarkan category pakai GET, di dalam () adalah endpoint dari API
    @GET("filter.php")
    suspend fun getMealbyCategory(@Query("c") c: String) : mealResponse

    // untuk retrieve detail data meal berdasarkan id
    @GET("lookup.php")
    suspend fun getDetailMeal(@Query("i") i:String) : detailMealResponse
}