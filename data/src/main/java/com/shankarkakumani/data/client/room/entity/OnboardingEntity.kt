package com.shankarkakumani.data.client.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shankarkakumani.data.client.room.client.RoomConfig

@Entity(tableName = RoomConfig.TABLE_ONBOARDING)
data class OnboardingEntity(
    @PrimaryKey val id: String = "onboarding_data",
    val featureType: String = "manual_buy_education",
    val introTitle: String,
    val introSubtitle: String,
    val ctaLottie: String,
    val toolBarIcon: String,
    val toolBarText: String,
    val expandCardStayInterval: Int,
    val collapseCardTiltInterval: Int,
    val collapseExpandIntroInterval: Int,
    val bottomToCenterTranslationInterval: Int,
    val version: String = "1.0.0",
    val lastUpdated: Long = System.currentTimeMillis(),
    val isActive: Boolean = true
) 