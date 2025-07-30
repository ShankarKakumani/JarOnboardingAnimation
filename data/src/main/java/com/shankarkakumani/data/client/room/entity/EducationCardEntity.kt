package com.shankarkakumani.data.client.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.shankarkakumani.data.client.room.client.RoomConfig
import java.util.UUID

@Entity(
    tableName = RoomConfig.TABLE_EDUCATION_CARD,
    foreignKeys = [
        ForeignKey(
            entity = OnboardingEntity::class,
            parentColumns = ["id"],
            childColumns = ["onboardingId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [androidx.room.Index(value = ["onboardingId"])]
)
data class EducationCardEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val onboardingId: String,
    val image: String,
    val endGradient: String,
    val startGradient: String,
    val strokeEndColor: String,
    val backgroundColor: String,
    val expandStateText: String,
    val strokeStartColor: String,
    val collapsedStateText: String,
    val displayOrder: Int
) 