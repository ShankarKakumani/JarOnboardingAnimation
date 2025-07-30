package com.shankarkakumani.domain.di

import com.shankarkakumani.domain.repository.repoInterface.OnboardingRepository
import com.shankarkakumani.domain.usecase.onboarding.usecase.ClearOnboardingCacheUseCase
import com.shankarkakumani.domain.usecase.onboarding.usecase.GetOnboardingDataUseCase
import com.shankarkakumani.domain.usecase.onboarding.usecase.RefreshOnboardingDataUseCase
import com.shankarkakumani.domain.usecase.onboarding.usecase.ValidateAnimationConfigUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    
    @Provides
    @Singleton
    fun provideGetOnboardingDataUseCase(
        repository: OnboardingRepository
    ): GetOnboardingDataUseCase = GetOnboardingDataUseCase(repository)
    
    @Provides
    @Singleton
    fun provideRefreshOnboardingDataUseCase(
        repository: OnboardingRepository
    ): RefreshOnboardingDataUseCase = RefreshOnboardingDataUseCase(repository)
    
    @Provides
    @Singleton
    fun provideValidateAnimationConfigUseCase(): ValidateAnimationConfigUseCase = 
        ValidateAnimationConfigUseCase()
    
    @Provides
    @Singleton
    fun provideClearOnboardingCacheUseCase(
        repository: OnboardingRepository
    ): ClearOnboardingCacheUseCase = ClearOnboardingCacheUseCase(repository)
} 