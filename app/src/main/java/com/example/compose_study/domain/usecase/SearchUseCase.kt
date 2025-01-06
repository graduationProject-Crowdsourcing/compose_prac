package com.example.compose_study.domain.usecase

import com.example.compose_study.domain.model.SearchListEntity
import com.example.compose_study.domain.repository.SearchRepository
import java.util.Date
import java.util.UUID

class SearchUseCase(
    private val searchRepository: SearchRepository
) {
    suspend fun createListItem(query: String): List<SearchListEntity>{
        val imageItems = searchRepository.getSearchImage(query = query).documents.map {
            SearchListEntity(
                id = UUID.randomUUID().toString(),
                title = it.displaySitename ?: "",
                thumbnail = it.thumbnailUrl ?: "",
                date = it.datetime ?: Date()
            )
        }
        val videoItems = searchRepository.getSearchVideo(query = query).documents.map {
            SearchListEntity(
                id = UUID.randomUUID().toString(),
                title = it.title,
                thumbnail = it.thumbnail,
                date = it.datetime ?: Date()
            )
        }

        return arrayListOf<SearchListEntity>().apply {
            addAll(imageItems)
            addAll(videoItems)
        }.sortedByDescending {
            it.date
        }
    }
}