package com.shankarkakumani.data.client.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class OnboardingWithDetailsEntity(
    @Embedded val onboarding: OnboardingEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "onboardingId"
    )
    val educationCards: List<EducationCardEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "onboardingId"
    )
    val saveButton: SaveButtonCtaEntity
) 