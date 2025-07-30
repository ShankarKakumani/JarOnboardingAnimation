package com.shankarkakumani.data.client.room.client

import android.content.Context
import androidx.room.Room
import com.shankarkakumani.data.client.room.database.JarAppDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomClientProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun provideDatabase(): JarAppDatabase {
        return Room.databaseBuilder(
            context,
            JarAppDatabase::class.java,
            RoomConfig.DATABASE_NAME
        )
        .fallbackToDestructiveMigration()
        .build()
    }
} 