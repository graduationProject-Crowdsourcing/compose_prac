package com.example.compose_study.data.service

import com.example.compose_study.data.response.ImageDocumentResponse
import com.example.compose_study.data.response.SearchResponse
import com.example.compose_study.data.response.VideoDocumentResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("/v2/search/image")
    suspend fun getSearchImage(
        @Query("query") query: String,
        @Query("sort") sort: String = "accuracy", // accuracy,recency
        @Query("page") page: Int=1, // 1~50
        @Query("size") size: Int = 80 // 1~80
    ): SearchResponse<ImageDocumentResponse>

    @GET("/v2/search/vclip")
    suspend fun getSearchVideo(
        @Query("query") query: String,
        @Query("sort") sort: String = "accuracy", // accuracy,recency
        @Query("page") page: Int = 1, // 1~15
        @Query("size") size: Int = 30 // 1~30
    ): SearchResponse<VideoDocumentResponse>
}