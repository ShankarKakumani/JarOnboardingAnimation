package com.shankarkakumani.domain.resource.input

import com.shankarkakumani.common.result.Result
import com.shankarkakumani.domain.resource.enum.CacheStrategyEnum
import com.shankarkakumani.domain.resource.enum.NetworkClientTypeEnum

data class GetOnboardingDataInput(
    val forceRefresh: Boolean = false,
    val cacheStrategy: CacheStrategyEnum = CacheStrategyEnum.CACHE_FIRST,
    val networkClientType: NetworkClientTypeEnum = NetworkClientTypeEnum.RETROFIT
) {
    fun validate(): Result<Unit> {
        return Result.Success(Unit)
    }
    
    fun shouldBypassCache(): Boolean = forceRefresh || cacheStrategy == CacheStrategyEnum.NETWORK_ONLY
} 