package com.example.searchapp.di

import android.app.Application
import androidx.room.Room
import com.example.searchapp.data.local.BookmarkDao
import com.example.searchapp.data.local.BookmarkDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    // BookmarkDatabase를 Singleton으로 제공
    @Provides
    @Singleton
    fun provideDatabase(app: Application): BookmarkDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            BookmarkDatabase::class.java,
            "bookmark_database"
        ).build()
    }

    // BookmarkDao를 제공
    @Provides
    fun provideBookmarkDao(database: BookmarkDatabase): BookmarkDao {
        return database.bookmarkDao()
    }
}