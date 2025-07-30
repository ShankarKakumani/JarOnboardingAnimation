package com.shankarkakumani.data.client.ktor.api

import com.shankarkakumani.data.dto.OnboardingApiResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class OnboardingKtorService @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getOnboardingData(): OnboardingApiResponseDto {
        return httpClient.get("796729cca6c55a7d089e").body()
    }
} 