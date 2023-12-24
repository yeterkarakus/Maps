package com.yeterkarakus.maps.api

import com.yeterkarakus.maps.data.rewievsdata.Reviews
import com.yeterkarakus.maps.data.searchdata.SearchNearby
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("/search-nearby")
    suspend fun searchNearby(@Query("query") query :String,
                             @Query("lat") lat:Number,
                             @Query("lng") lng:Number,
                             @Query("limit") limit:String,
                             @Query("language") language:String,
                             @Query("region") region:String ) : Response<SearchNearby>


    @GET("/business-reviews")
    suspend fun reviews(@Query("business_id") business_id :String,
                        @Query("limit") limit:Number,
                        @Query("language") language:String,
                        @Query("region") region:String) : Response<Reviews>
}