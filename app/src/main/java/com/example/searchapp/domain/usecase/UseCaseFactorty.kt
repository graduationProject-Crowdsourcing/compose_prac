package com.example.searchapp.domain.usecase

import com.example.searchapp.data.repository.SearchRepositoryImpl
import com.example.searchapp.data.service.RetrofitClient

object UseCaseFactorty{

    fun createSearchUseCase() = SearchUseCase(SearchRepositoryImpl(RetrofitClient.searchApiService))

}