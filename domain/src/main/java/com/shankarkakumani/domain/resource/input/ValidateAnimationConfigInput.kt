package com.shankarkakumani.domain.resource.input

import com.shankarkakumani.common.result.Result
import com.shankarkakumani.domain.resource.model.AnimationConfigModel
import com.shankarkakumani.domain.resource.exception.InvalidAnimationConfigException

data class ValidateAnimationConfigInput(
    val config: AnimationConfigModel
) {
    fun validate(): Result<Unit> {
        return when {
            !config.isValid() -> Result.Error(
                InvalidAnimationConfigException("Animation config contains invalid values")
            )
            !config.isReasonableSpeed() -> Result.Error(
                InvalidAnimationConfigException("Animation duration is unreasonable: ${config.getTotalAnimationDuration()}ms")
            )
            else -> Result.Success(Unit)
        }
    }
} 