package com.jihyun.compose_study.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jihyun.compose_study.data.database.entity.ImageItem
import com.jihyun.compose_study.data.database.entity.VideoItem
import com.jihyun.compose_study.network.KakaoApi
import com.jihyun.compose_study.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MediaViewModel : ViewModel() {
    private val _imageItems = MutableStateFlow<List<ImageItem>?>(null)
    val imageItems: StateFlow<List<ImageItem>?> = _imageItems

    private val _videoItems = MutableStateFlow<List<VideoItem>?>(null)
    val videoItems: StateFlow<List<VideoItem>?> = _videoItems
    private val kakaoApi = RetrofitClient.retrofit.create(KakaoApi::class.java)

    // 이미지 검색
    fun fetchMedia(query: String) {
        viewModelScope.launch {
            try {
                val response = kakaoApi.searchImages(query = query)
                _imageItems.value = response.documents // List<ImageItem>
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 동영상 검색
    fun fetchVideos(query: String) {
        viewModelScope.launch {
            try {
                Log.d("fetchVideos", "API 호출 시작: query=$query") // API 호출 전

                val response = kakaoApi.searchVideos(query = query)

                Log.d("fetchVideos", "API 응답 수신: $response") // API 응답 확인

                _videoItems.value = response.documents // List<VideoItem>


            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("fetchVideos", "Error fetching videos: ${e.message}") // 예외 처리
            }
        }
    }

}