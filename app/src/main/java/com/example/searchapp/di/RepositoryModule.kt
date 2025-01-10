package com.example.searchapp.di

import com.example.searchapp.data.repository.BookmarkRepositoryImpl
import com.example.searchapp.data.repository.SearchRepositoryImpl
import com.example.searchapp.data.service.SearchApiService
import com.example.searchapp.domain.repository.BookmarkRepository
import com.example.searchapp.domain.repository.SearchRepository
import com.example.searchapp.data.local.BookmarkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSearchRepository(apiService: SearchApiService): SearchRepository {
        return SearchRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideBookmarkRepository(bookmarkDao: BookmarkDao): BookmarkRepository {
        return BookmarkRepositoryImpl(bookmarkDao)
    }

}