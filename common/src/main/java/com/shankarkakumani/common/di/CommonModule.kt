package com.shankarkakumani.common.di

import com.shankarkakumani.common.constants.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides @Named("baseUrl") fun provideBaseUrl(): String = AppConstants.BASE_URL

    @Provides @Named("connectTimeout") fun provideConnectTimeout(): Long = 30_000L

    @Provides @Named("readTimeout") fun provideReadTimeout(): Long = 30_000L
}
