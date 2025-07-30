package com.shankarkakumani.data.di

import com.shankarkakumani.data.client.room.client.RoomClientProvider
import com.shankarkakumani.data.client.room.dao.OnboardingDao
import com.shankarkakumani.data.client.room.database.JarAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(provider: RoomClientProvider): JarAppDatabase =
        provider.provideDatabase()
    
    @Provides
    @Singleton
    fun provideOnboardingDao(database: JarAppDatabase): OnboardingDao =
        database.onboardingDao()
} 