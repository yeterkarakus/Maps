package com.yeterkarakus.maps

import com.yeterkarakus.maps.api.RetrofitAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun injectRetrofit() : RetrofitAPI {
        return  Retrofit.Builder()
                .baseUrl("https://local-business-data.p.rapidapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitAPI::class.java)
    }

}