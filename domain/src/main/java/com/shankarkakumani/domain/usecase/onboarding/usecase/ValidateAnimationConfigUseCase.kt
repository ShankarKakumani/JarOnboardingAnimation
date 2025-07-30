package com.shankarkakumani.domain.usecase.onboarding.usecase

import com.shankarkakumani.common.result.Result
import com.shankarkakumani.domain.resource.exception.InvalidAnimationConfigException
import com.shankarkakumani.domain.resource.input.ValidateAnimationConfigInput
import com.shankarkakumani.domain.resource.model.AnimationConfigModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

class ValidateAnimationConfigUseCase @Inject constructor() {
    
    suspend operator fun invoke(
        input: ValidateAnimationConfigInput
    ): Result<AnimationConfigModel> {
        
        // Validate input
        when (val validationResult = input.validate()) {
            is Result.Error -> return validationResult
            is Result.Loading -> return validationResult
            is Result.Success -> { /* Continue */ }
        }
        
        val config = input.config
        
        return when {
            !config.isValid() -> Result.Error(
                InvalidAnimationConfigException("Invalid animation configuration")
            )
            !config.isReasonableSpeed() -> Result.Error(
                InvalidAnimationConfigException(
                    "Animation duration ${config.getTotalAnimationDuration()}ms is not reasonable"
                )
            )
            else -> Result.Success(config)
        }
    }
} 