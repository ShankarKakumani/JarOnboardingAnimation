package com.shankarkakumani.data.client.retrofit.api

import com.shankarkakumani.data.dto.OnboardingApiResponseDto
import retrofit2.http.GET

interface OnboardingRetrofitService {
    @GET("796729cca6c55a7d089e")
    suspend fun getOnboardingData(): OnboardingApiResponseDto
} 