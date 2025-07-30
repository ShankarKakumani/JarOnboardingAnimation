package com.shankarkakumani.domain.resource.input

import com.shankarkakumani.common.result.Result
import com.shankarkakumani.domain.resource.enum.NetworkClientTypeEnum

data class RefreshOnboardingDataInput(
    val clearCache: Boolean = true,
    val networkClientType: NetworkClientTypeEnum = NetworkClientTypeEnum.RETROFIT
) {
    fun validate(): Result<Unit> {
        return Result.Success(Unit)
    }
} 