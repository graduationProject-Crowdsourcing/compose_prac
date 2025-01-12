package com.jihyun.compose_study.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jihyun.compose_study.data.database.dao.BookmarkDao
import com.jihyun.compose_study.data.database.entity.BookmarkEntity

@Database(entities = [BookmarkEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}
