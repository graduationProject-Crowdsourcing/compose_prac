package com.example.searchapp.data.local

import android.content.Context
import androidx.room.Room


// singleton으로 단일 인스턴스로 관리
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
