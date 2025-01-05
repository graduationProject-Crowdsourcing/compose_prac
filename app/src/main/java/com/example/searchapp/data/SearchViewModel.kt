package com.example.searchapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchapp.bookmark.BookmarkEntity
import com.example.searchapp.network.RetrofitClient
import kotlinx.coroutines.launch
import java.util.UUID


class SearchViewModel(
    private val bookmarkViewModel: BookmarkViewModel //의존성 추가
) : ViewModel() {

    private val _searchResults = MutableLiveData<UiState>()
    val searchResults: LiveData<UiState> get() = _searchResults

    private val apiService = RetrofitClient.create()

    init {
        _searchResults.value = UiState()
        Log.d("SearchViewModel", "ViewModel 초기화됨: ${_searchResults.value}")
    }

    fun search(query: String) {
        Log.d("SearchViewModel", "search() 호출됨, query: $query")

        // 로딩 상태 업데이트
        _searchResults.value = _searchResults.value?.copy(isLoading = true)
        Log.d("SearchViewModel", "로딩 상태 업데이트: ${_searchResults.value}")

        viewModelScope.launch {
            val allResults = mutableListOf<SearchItem>()
            try {
                // 이미지 검색
                val imageResponse = apiService.searchImage(query)
                Log.d("SearchViewModel", "이미지 검색 응답 코드: ${imageResponse.code()}")
                if (imageResponse.isSuccessful) {
                    Log.d("SearchViewModel", "이미지 검색 성공")
                    imageResponse.body()?.documents?.forEach { document ->
                        try {
                            val imageItem = SearchItem.ImageItem(
                                id = document.doc_url,
                                title = document.display_sitename,
                                thumbnail = document.thumbnail_url,
                                date = document.datetime
                            )
                            allResults.add(imageItem)
                            Log.d("SearchViewModel", "추가된 이미지 아이템: $imageItem")
                        } catch (e: Exception) {
                            Log.e("SearchViewModel", "이미지 데이터 변환 중 오류 발생: ${e.message}")
                        }
                    }
                } else {
                    Log.w("SearchViewModel", "이미지 검색 실패: ${imageResponse.code()} - ${imageResponse.message()}")
                }

                // 동영상 검색
                val videoResponse = apiService.searchVideo(query)
                Log.d("SearchViewModel", "동영상 검색 응답 코드: ${videoResponse.code()}")
                if (videoResponse.isSuccessful) {
                    Log.d("SearchViewModel", "동영상 검색 성공")
                    videoResponse.body()?.documents?.forEach { document ->
                        try {
                            val videoItem = SearchItem.VideoItem(
                                id = document.url,
                                title = document.title,
                                thumbnail = document.thumbnail,
                                date = document.datetime
                            )
                            allResults.add(videoItem)
                            Log.d("SearchViewModel", "추가된 동영상 아이템: $videoItem")
                        } catch (e: Exception) {
                            Log.e("SearchViewModel", "동영상 데이터 변환 중 오류 발생: ${e.message}")
                        }
                    }
                } else {
                    Log.w("SearchViewModel", "동영상 검색 실패: ${videoResponse.code()} - ${videoResponse.message()}")
                }

                syncBookmarks(allResults)

                // 결과 업데이트
                _searchResults.value = _searchResults.value?.copy(
                    searchItem = allResults,
                    isLoading = false
                )
                Log.d("SearchViewModel", "LiveData 최종 업데이트: ${_searchResults.value}")

            } catch (e: Exception) {
                Log.e("SearchViewModel", "API 호출 중 오류 발생: ${e.message}")
            } finally {
                _searchResults.value = _searchResults.value?.copy(isLoading = false)
                Log.d("SearchViewModel", "로딩 상태 초기화: ${_searchResults.value}")
            }
        }
    }

    fun toggleBookmark(item: SearchItem) {
        viewModelScope.launch {
            val updatedItem = when (item) {
                is SearchItem.ImageItem -> item.copy(bookmarked = !item.bookmarked)
                is SearchItem.VideoItem -> item.copy(bookmarked = !item.bookmarked)
            }

            val updatedList = _searchResults.value?.searchItem?.map {
                if (it.id == updatedItem.id) updatedItem else it
            } ?: emptyList()

            _searchResults.value = _searchResults.value?.copy(
                searchItem = updatedList
            )

            // BookmarkViewModel을 통해 북마크 상태 관리
            if (updatedItem.bookmarked) {
                bookmarkViewModel.addBookmark(
                    BookmarkEntity(
                        id = updatedItem.id,
                        title = updatedItem.title,
                        thumbnail = when (updatedItem) {
                            is SearchItem.ImageItem -> updatedItem.thumbnail
                            is SearchItem.VideoItem -> updatedItem.thumbnail
                        },
                        date = updatedItem.date,
                        userId = bookmarkViewModel.currentUserId.orEmpty()
                    )
                )
            } else {
                bookmarkViewModel.removeBookmark(
                    BookmarkEntity(
                        id = updatedItem.id,
                        title = updatedItem.title,
                        thumbnail = when (updatedItem) {
                            is SearchItem.ImageItem -> updatedItem.thumbnail
                            is SearchItem.VideoItem -> updatedItem.thumbnail
                        },
                        date = updatedItem.date,
                        userId = bookmarkViewModel.currentUserId.orEmpty()
                    )
                )
            }
        }
    }


    private fun syncBookmarks(results: MutableList<SearchItem>) {
        viewModelScope.launch {
            val bookmarkedIds = bookmarkViewModel.bookmarks.value?.map { it.id } ?: emptyList()
            results.replaceAll { result ->
                if (bookmarkedIds.contains(result.id)) {
                    when (result) {
                        is SearchItem.ImageItem -> result.copy(bookmarked = true)
                        is SearchItem.VideoItem -> result.copy(bookmarked = true)
                    }
                } else {
                    result
                }
            }
            _searchResults.value = _searchResults.value?.copy(searchItem = results)
        }
    }
}



data class UiState(
    val searchItem: List<SearchItem> = emptyList(),
    val bookmarkList: List<SearchItem> = emptyList(),
    val isLoading: Boolean = false
)




