package com.shankarkakumani.data.datasource.onboarding.local

import com.shankarkakumani.data.client.room.dao.OnboardingDao
import com.shankarkakumani.data.client.room.entity.*
import com.shankarkakumani.data.datasource.onboarding.LocalOnboardingDataSource
import javax.inject.Inject

class RoomOnboardingDataSource @Inject constructor(
    private val onboardingDao: OnboardingDao
) : LocalOnboardingDataSource {
    
    override suspend fun getOnboardingData(): OnboardingWithDetailsEntity? {
        return onboardingDao.getOnboardingWithDetails()
    }
    
    override suspend fun saveOnboardingData(
        onboarding: OnboardingEntity,
        educationCards: List<EducationCardEntity>,
        saveButton: SaveButtonCtaEntity
    ) {
        onboardingDao.insertOnboardingData(onboarding, educationCards, saveButton)
    }
    
    override suspend fun clearOnboardingData() {
        onboardingDao.clearOnboardingData()
    }
    
    override suspend fun getLastUpdated(): Long? {
        return onboardingDao.getLastUpdated()
    }
} 