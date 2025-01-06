package com.example.compose_study.domain.usecase

import com.example.compose_study.data.repositroy.SearchRepositoryImpl
import com.example.compose_study.network.RetrofitClient

object UseCaseFactory {
    fun createSearchUseCase() = SearchUseCase(SearchRepositoryImpl(RetrofitClient.searchService))
}