package com.example.compose_study.ui.di

import com.example.compose_study.data.repositroy.SearchRepositoryImpl
import com.example.compose_study.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
abstract class ViewModelModule {
    @ViewModelScoped
    @Binds
    abstract fun bindSearchRepository(
        repository: SearchRepositoryImpl
    ): SearchRepository
}