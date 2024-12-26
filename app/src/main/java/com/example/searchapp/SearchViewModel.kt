package com.example.searchapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchViewModel : ViewModel() {

    private val _searchResults = MutableLiveData<List<SearchItem>>()
    val searchResults: LiveData<List<SearchItem>> get() = _searchResults

    private val apiKey = "05b9b10614cf902738ca5955ab2a8f3c"

    private val apiService = RetrofitClient.create()

    fun search(query: String) {
        Log.d("SearchViewModel", "search() 호출됨, query: $query")

        // coroutine scope API 호출 처리
        viewModelScope.launch {
            val allResults = mutableListOf<SearchItem>()
            try {
                // 이미지 검색
                val imageResponse = apiService.searchImage("KakaoAK $apiKey", query)
                if (imageResponse.isSuccessful) {
                    imageResponse.body()?.documents?.forEach { document ->
                        try {
                            allResults.add(
                                SearchItem.ImageItem(
                                    thumbnail_url = document["thumbnail_url"] as String,
                                    datetime = document["datetime"] as String,
                                    display_sitename = document["display_sitename"] as? String ?: "출처 없음",
                                    collection = document["collection"] as String,
                                    image_url = document["image_url"] as String,
                                    doc_url = document["doc_url"] as String,
                                    width = (document["width"] as Number).toInt(),
                                    height = (document["height"] as Number).toInt()
                                )
                            )
                        } catch (e: Exception) {
                            Log.e("SearchViewModel", "이미지 데이터 변환 중 오류 발생: ${e.message}")
                        }
                    }
                } else {
                    Log.w("SearchViewModel", "이미지 검색 실패: ${imageResponse.code()} - ${imageResponse.message()}")
                }

                // 동영상 검색
                val videoResponse = apiService.searchVideo("KakaoAK $apiKey", query)
                if (videoResponse.isSuccessful) {
                    videoResponse.body()?.documents?.forEach { document ->
                        try {
                            allResults.add(
                                SearchItem.VideoItem(
                                    thumbnail = document["thumbnail"] as String,
                                    datetime = document["datetime"] as String,
                                    author = document["author"] as? String ?: "작성자 없음",
                                    url = document["url"] as String,
                                    title = document["title"] as String,
                                    play_time = (document["play_time"] as Number).toInt()
                                )
                            )
                        } catch (e: Exception) {
                            Log.e("SearchViewModel", "동영상 데이터 변환 중 오류 발생: ${e.message}")
                        }
                    }
                } else {
                    Log.w("SearchViewModel", "동영상 검색 실패: ${videoResponse.code()} - ${videoResponse.message()}")
                }

                // 결과 업데이트
                _searchResults.value = allResults
                Log.d("SearchViewModel", "LiveData 최종 업데이트: ${allResults.size}개")

            } catch (e: Exception) {
                Log.e("SearchViewModel", "API 호출 중 오류 발생: ${e.message}")
            }
        }
    }
}




