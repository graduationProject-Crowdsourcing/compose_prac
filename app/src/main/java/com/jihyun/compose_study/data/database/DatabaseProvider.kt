package com.jihyun.compose_study.data.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "bookmark_database" // 데이터베이스 이름
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
