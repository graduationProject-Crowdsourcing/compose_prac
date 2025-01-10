package com.example.searchapp.domain.repository

import com.example.searchapp.domain.model.SearchItem

interface SearchRepository {
    suspend fun search(query : String) : List<SearchItem>
}