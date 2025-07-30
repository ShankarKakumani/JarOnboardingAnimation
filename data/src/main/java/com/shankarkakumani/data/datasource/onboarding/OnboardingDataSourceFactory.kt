package com.shankarkakumani.data.datasource.onboarding

import com.shankarkakumani.data.datasource.onboarding.local.RoomOnboardingDataSource
import com.shankarkakumani.data.datasource.onboarding.remote.KtorOnboardingDataSource
import com.shankarkakumani.data.datasource.onboarding.remote.RetrofitOnboardingDataSource
import com.shankarkakumani.domain.resource.enum.NetworkClientTypeEnum
import dagger.Lazy
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnboardingDataSourceFactory @Inject constructor(
    private val retrofitDataSource: Lazy<RetrofitOnboardingDataSource>,
    private val ktorDataSource: Lazy<KtorOnboardingDataSource>,
    private val roomDataSource: Lazy<RoomOnboardingDataSource>
) {
    fun createRemoteDataSource(clientType: NetworkClientTypeEnum): OnboardingDataSource {
        return when (clientType) {
            NetworkClientTypeEnum.RETROFIT -> retrofitDataSource.get()
            NetworkClientTypeEnum.KTOR -> ktorDataSource.get()
        }
    }
    
    fun createLocalDataSource(): LocalOnboardingDataSource {
        return roomDataSource.get()
    }
} 