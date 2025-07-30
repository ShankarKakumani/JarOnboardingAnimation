package com.shankarkakumani.data.datasource.onboarding.remote

import com.shankarkakumani.data.client.retrofit.api.OnboardingRetrofitService
import com.shankarkakumani.data.datasource.onboarding.OnboardingDataSource
import com.shankarkakumani.data.dto.OnboardingApiResponseDto
import javax.inject.Inject

class RetrofitOnboardingDataSource @Inject constructor(
    private val apiService: OnboardingRetrofitService
) : OnboardingDataSource {
    override suspend fun getOnboardingData(): OnboardingApiResponseDto {
        return apiService.getOnboardingData()
    }
} 