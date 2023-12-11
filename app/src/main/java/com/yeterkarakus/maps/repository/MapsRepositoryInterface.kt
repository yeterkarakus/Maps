package com.yeterkarakus.maps.repository

import com.yeterkarakus.maps.data.searchdata.SearchNearby
import com.yeterkarakus.maps.util.Result

interface MapsRepositoryInterface {
    suspend fun searchLocation(query : String,lat : Number,lng : Number, limit : String,
                               language : String , region : String) : Result<SearchNearby>
}