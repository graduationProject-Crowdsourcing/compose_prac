package com.example.searchapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchapp.UserPreferences
import com.example.searchapp.domain.model.BookmarkEntity
import com.example.searchapp.domain.model.SearchItem
import com.example.searchapp.domain.repository.BookmarkRepository
import com.example.searchapp.domain.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val bookmarkRepository: BookmarkRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _searchResults = MutableLiveData<UiState>()
    val searchResults: LiveData<UiState> get() = _searchResults

//    private val apiService = RetrofitClient.searchApiService
    val currentUserId: String? get() = userPreferences.userId.value

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
            try {
                val results = searchUseCase.getResult(query)
                syncBookmarks(results.toMutableList())
                _searchResults.value = _searchResults.value?.copy(isLoading = false)
            } catch (e : Exception){
                Log.e("SearchViewModel", "API 호출 중 오류 발생: ${e.message}")
            } finally {
                _searchResults.value = _searchResults.value?.copy(isLoading = false)
                Log.d("SearchViewModel", "로딩 상태 초기화: ${_searchResults.value}")
            }
            /*
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

                // 결과 보여주기 전에 bookmark 동기화
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

             */
        }
    }

    // 특정 SearchItem 마다 bookmark 상태 변경 => UI와 DB에 동기화
    fun toggleBookmark(item: SearchItem) {
        viewModelScope.launch {

            // bookmark 상태 반전
            val updatedItem = when (item) {
                is SearchItem.ImageItem -> item.copy(bookmarked = !item.bookmarked)
                is SearchItem.VideoItem -> item.copy(bookmarked = !item.bookmarked)
            }

            // UI 반영
            val updatedList = _searchResults.value?.searchItem?.map {
                if (it.id == updatedItem.id) updatedItem else it
            } ?: emptyList()

            _searchResults.value = _searchResults.value?.copy(
                searchItem = updatedList
            )

            val bookmarkEntity = BookmarkEntity(
                id = updatedItem.id,
                title = updatedItem.title,
                thumbnail = when (updatedItem) {
                    is SearchItem.ImageItem -> updatedItem.thumbnail
                    is SearchItem.VideoItem -> updatedItem.thumbnail
                },
                date = updatedItem.date,
                userId = currentUserId.orEmpty()
            )

            if (updatedItem.bookmarked) {
                bookmarkRepository.addBookmark(bookmarkEntity)
            } else {
                bookmarkRepository.removeBookmark(bookmarkEntity)
            }

            // BookmarkViewModel을 통해 북마크 db 상태 관리
//            if (updatedItem.bookmarked) {
//                bookmarkRepository.addBookmark(
//                    BookmarkEntity(
//                        id = updatedItem.id,
//                        title = updatedItem.title,
//                        thumbnail = when (updatedItem) {
//                            is SearchItem.ImageItem -> updatedItem.thumbnail
//                            is SearchItem.VideoItem -> updatedItem.thumbnail
//                        },
//                        date = updatedItem.date,
//                        userId = currentUserId.orEmpty()
//                    )
//                )
//            } else {
//                bookmarkRepository.removeBookmark(
//                    BookmarkEntity(
//                        id = updatedItem.id,
//                        title = updatedItem.title,
//                        thumbnail = when (updatedItem) {
//                            is SearchItem.ImageItem -> updatedItem.thumbnail
//                            is SearchItem.VideoItem -> updatedItem.thumbnail
//                        },
//                        date = updatedItem.date,
//                        userId = currentUserId.orEmpty()
//                    )
//                )
//            }
        }
    }

    // bookmark 동기화 => 검색 결과화면에서 북마크되어 있다면 북마크 활성화
//    private fun syncBookmarks(results: MutableList<SearchItem>) {
//        viewModelScope.launch {
//            results.replaceAll { result ->
//                if (currentUserId?.contains(result.id) == true) {
//                    when (result) {
//                        is SearchItem.ImageItem -> result.copy(bookmarked = true)
//                        is SearchItem.VideoItem -> result.copy(bookmarked = true)
//                    }
//                } else {
//                    result
//                }
//            }
//            _searchResults.value = _searchResults.value?.copy(searchItem = results)
//        }
//    }
    private fun syncBookmarks(results: MutableList<SearchItem>) {
        viewModelScope.launch {
            val bookmarks = bookmarkRepository.bookmarks.value // 최신 북마크 데이터를 가져옴

            val updatedResults = results.map { result ->
                if (bookmarks.any { it.id == result.id }) {
                    // 북마크와 일치하면 bookmarked 상태를 true로 설정
                    when (result) {
                        is SearchItem.ImageItem -> result.copy(bookmarked = true)
                        is SearchItem.VideoItem -> result.copy(bookmarked = true)
                    }
                } else {
                    // bookmarked 상태를 false로 유지
                    result
                }
            }

            // 변경된 결과를 UI 상태에 반영
            _searchResults.value = _searchResults.value?.copy(searchItem = results)
        }
    }

}


data class UiState(
    val searchItem: List<SearchItem> = emptyList(),
    val bookmarkList: List<SearchItem> = emptyList(),
    val isLoading: Boolean = false
)




