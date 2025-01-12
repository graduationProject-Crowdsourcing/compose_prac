package com.jihyun.compose_study.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jihyun.compose_study.domain.model.BookmarkModel
import com.jihyun.compose_study.domain.usecase.AddBookmarkUseCase
import com.jihyun.compose_study.domain.usecase.DeleteBookmarkUseCase
import com.jihyun.compose_study.domain.usecase.GetBookmarksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val getBookmarksUseCase: GetBookmarksUseCase,
    private val addBookmarkUseCase: AddBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase
) : ViewModel() {

    // 북마크 목록 상태 관리
    private val _bookmarks = MutableStateFlow<List<BookmarkModel>>(emptyList())
    val bookmarks: StateFlow<List<BookmarkModel>> = _bookmarks

    // 북마크 목록 불러오기
    fun loadBookmarks() {
        viewModelScope.launch {
            getBookmarksUseCase()
                .collect { bookmarks ->
                    _bookmarks.value = bookmarks
                }
        }
    }

    // 북마크 추가
    fun addBookmark(bookmark: BookmarkModel) {
        viewModelScope.launch {
            addBookmarkUseCase(bookmark)
            loadBookmarks() // 추가 후 리스트 갱신
        }
    }

    // 북마크 삭제
    fun deleteBookmark(bookmark: BookmarkModel) {
        viewModelScope.launch {
            deleteBookmarkUseCase(bookmark)
            loadBookmarks() // 삭제 후 리스트 갱신
        }
    }
}
