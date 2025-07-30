package com.shankarkakumani.data.client.room.dao

import androidx.room.*
import com.shankarkakumani.data.client.room.client.RoomConfig
import com.shankarkakumani.data.client.room.entity.*

@Dao
interface OnboardingDao {
    @Transaction
    @Query("SELECT * FROM ${RoomConfig.TABLE_ONBOARDING} WHERE id = :id AND isActive = 1")
    suspend fun getOnboardingWithDetails(id: String = "onboarding_data"): OnboardingWithDetailsEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOnboarding(onboarding: OnboardingEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEducationCards(cards: List<EducationCardEntity>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSaveButtonCta(saveButton: SaveButtonCtaEntity)
    
    @Transaction
    suspend fun insertOnboardingData(
        onboarding: OnboardingEntity,
        educationCards: List<EducationCardEntity>,
        saveButton: SaveButtonCtaEntity
    ) {
        insertOnboarding(onboarding)
        insertEducationCards(educationCards)
        insertSaveButtonCta(saveButton)
    }
    
    @Query("SELECT lastUpdated FROM ${RoomConfig.TABLE_ONBOARDING} WHERE id = :id")
    suspend fun getLastUpdated(id: String = "onboarding_data"): Long?
    
    @Query("DELETE FROM ${RoomConfig.TABLE_ONBOARDING}")
    suspend fun clearOnboardingData()
} 