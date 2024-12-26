package com.example.searchapp


import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchApiService {

    // 이미지 검색
    @GET("v2/search/image")
    suspend fun searchImage(
        @Header("Authorization") apiKey : String,
        @Query("query") query : String,
        @Query("size") size : Int = 10
    ): Response<KakaoSearchResponse>

    // 동영상 검색
    @GET("v2/search/vclip")
    suspend fun searchVideo(
        @Header("Authorization") apiKey : String,
        @Query("query") query : String,
        @Query("size") size : Int = 10
    ): Response<KakaoSearchResponse>
}