package com.example.compose_study.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var INSTANCE: BookmarkDatabase? = null

    fun getDatabase(context: Context): BookmarkDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                BookmarkDatabase::class.java,
                "bookmark_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
