package com.shankarkakumani.data.datasource.onboarding

import com.shankarkakumani.data.client.room.entity.*
import com.shankarkakumani.data.dto.OnboardingApiResponseDto

interface OnboardingDataSource {
    suspend fun getOnboardingData(): OnboardingApiResponseDto
}

interface LocalOnboardingDataSource {
    suspend fun getOnboardingData(): OnboardingWithDetailsEntity?
    suspend fun saveOnboardingData(
        onboarding: OnboardingEntity,
        educationCards: List<EducationCardEntity>,
        saveButton: SaveButtonCtaEntity
    )
    suspend fun clearOnboardingData()
    suspend fun getLastUpdated(): Long?
} 