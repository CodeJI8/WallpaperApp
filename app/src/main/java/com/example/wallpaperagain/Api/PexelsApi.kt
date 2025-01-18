package com.example.wallpaperagain.Api

import com.example.wallpaperagain.Models.Wallpaper_model
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PexelsApi {

    @GET("v1/curated")
    fun getCuratedPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("api_key") apiKey: String = "Jg8BC544xGv6EvULXhr2nJuyW2Ow3ZVOtNhYth6EwnnZJ4js6f3tnRdU"
    ): Call<Wallpaper_model>
}
