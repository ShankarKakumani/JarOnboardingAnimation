package com.shankarkakumani.data.client.room.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.shankarkakumani.data.client.room.client.RoomConfig
import com.shankarkakumani.data.client.room.dao.OnboardingDao
import com.shankarkakumani.data.client.room.entity.*

@Database(
    entities = [
        OnboardingEntity::class,
        EducationCardEntity::class,
        SaveButtonCtaEntity::class
    ],
    version = RoomConfig.DATABASE_VERSION,
    exportSchema = false
)
abstract class JarAppDatabase : RoomDatabase() {
    abstract fun onboardingDao(): OnboardingDao
} 