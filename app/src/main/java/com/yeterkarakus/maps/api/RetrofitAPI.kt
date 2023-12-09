package com.yeterkarakus.maps.api

import com.yeterkarakus.maps.data.SearchNearby
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitAPI {

    @Headers(
            "X-RapidAPI-Key: ",
            "X-RapidAPI-Host: ")
    @GET("/search-nearby")
    suspend fun searchNearby(@Query("query") query :String,
                             @Query("lat") lat:Number,
                             @Query("lng") lng:Number,
                             @Query("limit") limit:String,
                             @Query("language") language:String,
                             @Query("region") region:String ) : Response<SearchNearby>
}