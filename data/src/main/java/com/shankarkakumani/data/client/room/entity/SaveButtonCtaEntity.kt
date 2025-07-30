package com.shankarkakumani.data.client.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.shankarkakumani.data.client.room.client.RoomConfig

@Entity(
    tableName = RoomConfig.TABLE_SAVE_BUTTON_CTA,
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
data class SaveButtonCtaEntity(
    @PrimaryKey val id: String = "save_button_cta",
    val onboardingId: String,
    val text: String,
    val textColor: String,
    val strokeColor: String,
    val backgroundColor: String,
    val icon: String?,
    val deeplink: String?
) 