package com.shankarkakumani.domain.usecase.onboarding.usecase

import com.shankarkakumani.common.result.Result
import com.shankarkakumani.domain.repository.repoInterface.OnboardingRepository
import com.shankarkakumani.domain.resource.exception.OnboardingDomainException
import com.shankarkakumani.domain.resource.input.RefreshOnboardingDataInput
import com.shankarkakumani.domain.resource.model.OnboardingDataModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

class RefreshOnboardingDataUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    
    suspend operator fun invoke(
        input: RefreshOnboardingDataInput = RefreshOnboardingDataInput()
    ): Result<OnboardingDataModel> {
        
        // Validate input
        when (val validationResult = input.validate()) {
            is Result.Error -> return validationResult
            is Result.Loading -> return validationResult
            is Result.Success -> { /* Continue */ }
        }
        
        return try {
            repository.refreshOnboardingData(
                clearCache = input.clearCache,
                networkClientType = input.networkClientType
            )
        } catch (e: Exception) {
            Result.Error(OnboardingDomainException("Failed to refresh onboarding data", e))
        }
    }
} 