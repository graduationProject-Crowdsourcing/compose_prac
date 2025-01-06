package com.example.compose_study.domain.repository

import com.example.compose_study.data.service.SearchService
import com.example.compose_study.domain.model.ImageDocumentEntity
import com.example.compose_study.domain.model.SearchEntity
import com.example.compose_study.domain.model.VideoDocumentEntity

interface SearchRepository {
    suspend fun getSearchImage(
        query: String,
        sort: String = "accuracy",
        page: Int = 1,
        size: Int = 80
    ): SearchEntity<ImageDocumentEntity>

    suspend fun getSearchVideo(
        query: String,
        sort: String = "accuracy",
        page: Int = 1,
        size: Int = 30
    ): SearchEntity<VideoDocumentEntity>
}