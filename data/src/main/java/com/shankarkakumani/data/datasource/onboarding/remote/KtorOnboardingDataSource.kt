package com.shankarkakumani.data.datasource.onboarding.remote

import com.shankarkakumani.data.client.ktor.api.OnboardingKtorService
import com.shankarkakumani.data.datasource.onboarding.OnboardingDataSource
import com.shankarkakumani.data.dto.OnboardingApiResponseDto
import javax.inject.Inject

class KtorOnboardingDataSource @Inject constructor(
    private val ktorService: OnboardingKtorService
) : OnboardingDataSource {
    override suspend fun getOnboardingData(): OnboardingApiResponseDto {
        return ktorService.getOnboardingData()
    }
} 