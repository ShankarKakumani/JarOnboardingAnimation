package com.shankarkakumani.data.repository

import com.shankarkakumani.common.result.Result
import com.shankarkakumani.data.datasource.onboarding.OnboardingDataSourceFactory
import com.shankarkakumani.data.mapper.*
import com.shankarkakumani.domain.repository.repoInterface.OnboardingRepository
import com.shankarkakumani.domain.resource.enum.CacheStrategyEnum
import com.shankarkakumani.domain.resource.enum.NetworkClientTypeEnum
import com.shankarkakumani.domain.resource.model.OnboardingDataModel
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val dataSourceFactory: OnboardingDataSourceFactory
) : OnboardingRepository {
    
    companion object {
        private const val CACHE_VALIDITY_DURATION = 24 * 60 * 60 * 1000L // 24 hours
    }
    
    override suspend fun getOnboardingData(
        cacheStrategy: CacheStrategyEnum,
        networkClientType: NetworkClientTypeEnum
    ): Result<OnboardingDataModel> {
        return try {
            when (cacheStrategy) {
                CacheStrategyEnum.CACHE_FIRST -> getCacheFirstData(networkClientType)
                CacheStrategyEnum.NETWORK_FIRST -> getNetworkFirstData(networkClientType)
                CacheStrategyEnum.CACHE_ONLY -> getCacheOnlyData()
                CacheStrategyEnum.NETWORK_ONLY -> getNetworkOnlyData(networkClientType)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun refreshOnboardingData(
        clearCache: Boolean,
        networkClientType: NetworkClientTypeEnum
    ): Result<OnboardingDataModel> {
        return try {
            if (clearCache) {
                clearCache()
            }
            getNetworkDataAndCache(networkClientType)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun clearCache(): Result<Unit> {
        return try {
            val localDataSource = dataSourceFactory.createLocalDataSource()
            localDataSource.clearOnboardingData()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun isCacheValid(): Result<Boolean> {
        return try {
            val localDataSource = dataSourceFactory.createLocalDataSource()
            val cachedData = localDataSource.getOnboardingData()
            val isValid = cachedData != null && !isCacheExpired(cachedData.onboarding.lastUpdated)
            Result.Success(isValid)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun getLastCacheUpdate(): Result<Long?> {
        return try {
            val localDataSource = dataSourceFactory.createLocalDataSource()
            val lastUpdated = localDataSource.getLastUpdated()
            Result.Success(lastUpdated)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    private suspend fun getCacheFirstData(networkClientType: NetworkClientTypeEnum): Result<OnboardingDataModel> {
        val localDataSource = dataSourceFactory.createLocalDataSource()
        val cachedData = localDataSource.getOnboardingData()
        
        return if (cachedData != null && !isCacheExpired(cachedData.onboarding.lastUpdated)) {
            Result.Success(cachedData.toOnboardingDataModel())
        } else {
            getNetworkDataAndCache(networkClientType)
        }
    }
    
    private suspend fun getNetworkFirstData(networkClientType: NetworkClientTypeEnum): Result<OnboardingDataModel> {
        return try {
            getNetworkDataAndCache(networkClientType)
        } catch (e: Exception) {
            val localDataSource = dataSourceFactory.createLocalDataSource()
            val cachedData = localDataSource.getOnboardingData()
            if (cachedData != null) {
                Result.Success(cachedData.toOnboardingDataModel())
            } else {
                Result.Error(e)
            }
        }
    }
    
    private suspend fun getCacheOnlyData(): Result<OnboardingDataModel> {
        val localDataSource = dataSourceFactory.createLocalDataSource()
        val cachedData = localDataSource.getOnboardingData()
        
        return if (cachedData != null) {
            Result.Success(cachedData.toOnboardingDataModel())
        } else {
            Result.Error(Exception("No cached data available"))
        }
    }
    
    private suspend fun getNetworkOnlyData(networkClientType: NetworkClientTypeEnum): Result<OnboardingDataModel> {
        return getNetworkDataAndCache(networkClientType)
    }
    
    private suspend fun getNetworkDataAndCache(networkClientType: NetworkClientTypeEnum): Result<OnboardingDataModel> {
        val remoteDataSource = dataSourceFactory.createRemoteDataSource(networkClientType)
        val dto = remoteDataSource.getOnboardingData()
        
        val localDataSource = dataSourceFactory.createLocalDataSource()
        localDataSource.saveOnboardingData(
            onboarding = dto.toOnboardingEntity(),
            educationCards = dto.data.onboardingScreen.educationCards.toEducationCardEntities(),
            saveButton = dto.data.onboardingScreen.saveButton.toSaveButtonCtaEntity()
        )
        
        return Result.Success(dto.toOnboardingDataModel())
    }
    
    private fun isCacheExpired(lastUpdated: Long): Boolean {
        return System.currentTimeMillis() - lastUpdated > CACHE_VALIDITY_DURATION
    }
} 