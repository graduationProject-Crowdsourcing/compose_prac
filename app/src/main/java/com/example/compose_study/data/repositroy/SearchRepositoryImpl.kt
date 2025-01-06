package com.example.compose_study.data.repositroy

import com.example.compose_study.data.service.SearchService
import com.example.compose_study.domain.model.toImageEntity
import com.example.compose_study.domain.model.toVideoEntity
import com.example.compose_study.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val searchService: SearchService
) : SearchRepository {
    override suspend fun getSearchImage(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ) = searchService.getSearchImage(
        query,
        sort,
        page,
        size
    ).toImageEntity()

    override suspend fun getSearchVideo(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ) = searchService.getSearchVideo(
        query,
        sort,
        page,
        size
    ).toVideoEntity()
}