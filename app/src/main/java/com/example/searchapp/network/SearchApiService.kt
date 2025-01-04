package com.example.searchapp.network


import com.example.searchapp.data.ImageResponse
import com.example.searchapp.data.KakaoSearchResponse
import com.example.searchapp.data.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {

    // 이미지 검색
    @GET("v2/search/image")
    suspend fun searchImage(
        @Query("query") query : String,
        @Query("size") size : Int = 10
    ): Response<KakaoSearchResponse<ImageResponse>>

    // 동영상 검색
    @GET("v2/search/vclip")
    suspend fun searchVideo(
        @Query("query") query : String,
        @Query("size") size : Int = 10
    ): Response<KakaoSearchResponse<VideoResponse>>
}