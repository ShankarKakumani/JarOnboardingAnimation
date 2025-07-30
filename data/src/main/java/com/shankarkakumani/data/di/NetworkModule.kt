package com.shankarkakumani.data.di

import com.shankarkakumani.data.client.ktor.api.OnboardingKtorService
import com.shankarkakumani.data.client.ktor.client.KtorClientProvider
import com.shankarkakumani.data.client.retrofit.api.OnboardingRetrofitService
import com.shankarkakumani.data.client.retrofit.client.RetrofitClientProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    @Provides
    @Singleton
    fun provideOkHttpClient(provider: RetrofitClientProvider): OkHttpClient =
        provider.provideOkHttpClient()
    
    @Provides
    @Singleton
    fun provideRetrofit(provider: RetrofitClientProvider, client: OkHttpClient): Retrofit =
        provider.provideRetrofit(client)
    
    @Provides
    @Singleton
    fun provideRetrofitService(retrofit: Retrofit): OnboardingRetrofitService =
        retrofit.create(OnboardingRetrofitService::class.java)
    
    @Provides
    @Singleton
    fun provideKtorClient(provider: KtorClientProvider): HttpClient =
        provider.provideHttpClient()
    
    @Provides
    @Singleton
    fun provideKtorService(httpClient: HttpClient): OnboardingKtorService =
        OnboardingKtorService(httpClient)
} 