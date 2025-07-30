package com.shankarkakumani.domain.usecase.onboarding.usecase

import com.shankarkakumani.common.result.Result
import com.shankarkakumani.domain.repository.repoInterface.OnboardingRepository
import com.shankarkakumani.domain.resource.exception.OnboardingDomainException
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

class ClearOnboardingCacheUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    
    suspend operator fun invoke(): Result<Unit> {
        return try {
            repository.clearCache()
        } catch (e: Exception) {
            Result.Error(OnboardingDomainException("Failed to clear cache", e))
        }
    }
} 