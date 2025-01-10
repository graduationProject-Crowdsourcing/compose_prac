package com.example.searchapp.di

import com.example.searchapp.domain.repository.SearchRepository
import com.example.searchapp.domain.usecase.SearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideSearchUseCase(searchRepository: SearchRepository): SearchUseCase {
        return SearchUseCase(searchRepository)
    }
}