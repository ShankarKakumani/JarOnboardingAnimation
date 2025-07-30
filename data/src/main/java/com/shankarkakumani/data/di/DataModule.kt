package com.shankarkakumani.data.di

import com.shankarkakumani.data.datasource.onboarding.OnboardingDataSourceFactory
import com.shankarkakumani.data.datasource.onboarding.local.RoomOnboardingDataSource
import com.shankarkakumani.data.datasource.onboarding.remote.KtorOnboardingDataSource
import com.shankarkakumani.data.datasource.onboarding.remote.RetrofitOnboardingDataSource
import com.shankarkakumani.data.repository.OnboardingRepositoryImpl
import com.shankarkakumani.domain.repository.repoInterface.OnboardingRepository
import dagger.Binds
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    
    @Binds
    @Singleton
    abstract fun bindOnboardingRepository(
        impl: OnboardingRepositoryImpl
    ): OnboardingRepository
    
    companion object {
        @Provides
        @Singleton
        fun provideDataSourceFactory(
            retrofit: Lazy<RetrofitOnboardingDataSource>,
            ktor: Lazy<KtorOnboardingDataSource>,
            room: Lazy<RoomOnboardingDataSource>
        ): OnboardingDataSourceFactory = OnboardingDataSourceFactory(retrofit, ktor, room)
    }
} 