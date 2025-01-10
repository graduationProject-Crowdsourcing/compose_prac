package com.example.searchapp.domain.usecase

import com.example.searchapp.domain.model.SearchItem
import com.example.searchapp.domain.repository.SearchRepository

class SearchUseCase(private val repository: SearchRepository) {
    // suspend fun ==> 비동기 작업을 coroutine으로 효율적으로 처리
    suspend fun getResult(query : String): List<SearchItem>{
        return repository.search(query)
    }
}