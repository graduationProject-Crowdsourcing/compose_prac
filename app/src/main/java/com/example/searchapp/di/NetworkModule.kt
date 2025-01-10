package com.example.searchapp.di

import com.example.searchapp.data.service.SearchApiService
import com.example.searchapp.data.service.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideSearchApiService(): SearchApiService {
        return RetrofitClient.searchApiService
    }
}