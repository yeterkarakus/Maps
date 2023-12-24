package com.yeterkarakus.maps.dependencyinjection

import com.yeterkarakus.maps.BuildConfig
import com.yeterkarakus.maps.api.RetrofitAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun interceptor() : Interceptor {
        return Interceptor {chain->
            val request = chain.request()
            val apiKey = BuildConfig.RapidAPI_Key
            val apiHost = BuildConfig.RapidAPI_Host
            val authenticatedRequest = request.newBuilder()
                .get()
                .addHeader("X-RapidAPI-Key",apiHost)
                .addHeader("X-RapidAPI-Host", apiKey)
                .build()
           return@Interceptor chain.proceed(authenticatedRequest)
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

    } @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://local-business-data.p.rapidapi.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Singleton
    @Provides
    fun injectRetrofit(retrofit: Retrofit) : RetrofitAPI {
        return retrofit.create(RetrofitAPI::class.java)
    }

}