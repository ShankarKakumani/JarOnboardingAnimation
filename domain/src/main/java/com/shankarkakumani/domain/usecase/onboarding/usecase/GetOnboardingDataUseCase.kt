package com.shankarkakumani.domain.usecase.onboarding.usecase

import com.shankarkakumani.common.result.Result
import com.shankarkakumani.domain.repository.repoInterface.OnboardingRepository
import com.shankarkakumani.domain.resource.exception.DataValidationException
import com.shankarkakumani.domain.resource.exception.NetworkUnavailableException
import com.shankarkakumani.domain.resource.exception.OnboardingDomainException
import com.shankarkakumani.domain.resource.input.GetOnboardingDataInput
import com.shankarkakumani.domain.resource.model.OnboardingDataModel
import java.io.IOException
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

class GetOnboardingDataUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    
    suspend operator fun invoke(
        input: GetOnboardingDataInput = GetOnboardingDataInput()
    ): Result<OnboardingDataModel> {
        
        // Validate input
        when (val validationResult = input.validate()) {
            is Result.Error -> return validationResult
            is Result.Loading -> return validationResult
            is Result.Success -> { /* Continue */ }
        }
        
        return try {
            val result = repository.getOnboardingData(
                cacheStrategy = input.cacheStrategy,
                networkClientType = input.networkClientType
            )
            
            when (result) {
                is Result.Success -> {
                    // Validate business rules
                    validateOnboardingData(result.data)
                }
                is Result.Error -> {
                    val domainError = when (result.exception) {
                        is IOException -> NetworkUnavailableException(cause = result.exception)
                        else -> OnboardingDomainException("Failed to get onboarding data", result.exception)
                    }
                    Result.Error(domainError)
                }
                is Result.Loading -> result
            }
        } catch (e: Exception) {
            Result.Error(OnboardingDomainException("Unexpected error occurred", e))
        }
    }
    
    private fun validateOnboardingData(data: OnboardingDataModel): Result<OnboardingDataModel> {
        return when {
            !data.isValid() -> Result.Error(
                DataValidationException("Onboarding data is invalid")
            )
            !data.hasMinimumCards() -> Result.Error(
                DataValidationException("Insufficient education cards", "educationCards")
            )
            data.getDisplayableCards().isEmpty() -> Result.Error(
                DataValidationException("No valid education cards found", "educationCards")
            )
            else -> Result.Success(data)
        }
    }
} 