package com.example.searchapp.data.repository

import com.example.searchapp.data.service.SearchApiService
import com.example.searchapp.domain.model.SearchItem
import com.example.searchapp.domain.repository.SearchRepository

class SearchRepositoryImpl(private val apiService: SearchApiService) : SearchRepository {
    override suspend fun search(query : String): List<SearchItem>{
        val results = mutableListOf<SearchItem>()

        val imageResponse = apiService.searchImage(query)
        if (imageResponse.isSuccessful) {
            results.addAll(imageResponse.body()?.documents?.map { document->
                SearchItem.ImageItem(
                    id = document.doc_url,
                    title = document.display_sitename,
                    thumbnail = document.thumbnail_url,
                    date = document.datetime
                )
            } ?: emptyList())
        }

        val videoResponse = apiService.searchVideo(query)
        if (videoResponse.isSuccessful) {
            results.addAll(videoResponse.body()?.documents?.map { document ->
                SearchItem.VideoItem(
                    id = document.url,
                    title = document.title,
                    thumbnail = document.thumbnail,
                    date = document.datetime
                )
            } ?: emptyList())
        }

        return results
    }
}