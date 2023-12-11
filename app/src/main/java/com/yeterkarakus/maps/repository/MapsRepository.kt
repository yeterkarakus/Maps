package com.yeterkarakus.maps.repository

import com.yeterkarakus.maps.api.RetrofitAPI
import com.yeterkarakus.maps.data.searchdata.SearchNearby
import com.yeterkarakus.maps.util.Result
import java.lang.Exception
import javax.inject.Inject

class MapsRepository@Inject constructor(private val retrofit : RetrofitAPI) :
    MapsRepositoryInterface {
    override suspend fun searchLocation(
        query: String,
        lat: Number,
        lng: Number,
        limit: String,
        language: String,
        region: String
    ): Result<SearchNearby> {
        return try {
            val data = retrofit.searchNearby(query, lat, lng, limit, language, region)
            if (data.isSuccessful){
                data.body()?.let{
                    return@let Result.success(it)
                }?: Result.error("Error Data !",null)
            }else{
                Result.error("Error Data!",null)
            }
        }catch (e : Exception){
            Result.error(e.message.toString(),null)
        }
    }

}