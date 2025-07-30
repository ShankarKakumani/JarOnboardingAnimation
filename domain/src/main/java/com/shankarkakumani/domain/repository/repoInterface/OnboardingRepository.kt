package com.shankarkakumani.domain.repository.repoInterface

import com.shankarkakumani.common.result.Result
import com.shankarkakumani.domain.resource.enum.CacheStrategyEnum
import com.shankarkakumani.domain.resource.enum.NetworkClientTypeEnum
import com.shankarkakumani.domain.resource.model.OnboardingDataModel

interface OnboardingRepository {
    
    /**
     * Retrieves onboarding data based on the specified strategy
     * @param cacheStrategy Strategy for cache vs network preference
     * @param networkClientType HTTP client to use for network calls
     * @return Result containing OnboardingDataModel or error
     */
    suspend fun getOnboardingData(
        cacheStrategy: CacheStrategyEnum = CacheStrategyEnum.CACHE_FIRST,
        networkClientType: NetworkClientTypeEnum = NetworkClientTypeEnum.RETROFIT
    ): Result<OnboardingDataModel>
    
    /**
     * Forces a refresh of onboarding data from network
     * @param clearCache Whether to clear existing cache before refresh
     * @param networkClientType HTTP client to use for network call
     * @return Result containing fresh OnboardingDataModel or error
     */
    suspend fun refreshOnboardingData(
        clearCache: Boolean = true,
        networkClientType: NetworkClientTypeEnum = NetworkClientTypeEnum.RETROFIT
    ): Result<OnboardingDataModel>
    
    /**
     * Clears all cached onboarding data
     * @return Result indicating success or failure
     */
    suspend fun clearCache(): Result<Unit>
    
    /**
     * Checks if cached data is available and valid
     * @return Result containing boolean indicating cache validity
     */
    suspend fun isCacheValid(): Result<Boolean>
    
    /**
     * Gets the timestamp of last cache update
     * @return Result containing timestamp or null if no cache exists
     */
    suspend fun getLastCacheUpdate(): Result<Long?>
} 