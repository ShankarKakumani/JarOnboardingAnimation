package com.shankarkakumani.data.client.retrofit.client

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
// Note: Using debug logging as default for development
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitClientProvider @Inject constructor() {
    
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }
    
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(RetrofitConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(RetrofitConfig.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(RetrofitConfig.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY  // Enable for development
            })
            .build()
    }
    
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(RetrofitConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
} 