package com.example.searchapp.bookmark

import androidx.room.Database
import androidx.room.RoomDatabase

// db 핵심클래스 정의
@Database(entities = [BookmarkEntity::class], version = 1)
abstract class BookmarkDatabase : RoomDatabase() {
    abstract fun bookmarkDao() : BookmarkDao
}