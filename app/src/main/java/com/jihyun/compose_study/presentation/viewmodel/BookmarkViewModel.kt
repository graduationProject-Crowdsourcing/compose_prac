package com.jihyun.compose_study.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jihyun.compose_study.data.database.dao.BookmarkDao
import com.jihyun.compose_study.data.database.entity.BookmarkEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookmarkViewModel(private val bookmarkDao: BookmarkDao) : ViewModel() {

    // 북마크 목록 상태 관리
    private val _bookmarks = MutableStateFlow<List<BookmarkEntity>>(emptyList())
    val bookmarks: StateFlow<List<BookmarkEntity>> = _bookmarks


    // 북마크 목록 불러오기
    fun loadBookmarks() {
        viewModelScope.launch {
            bookmarkDao.getAllBookmarks().collect { bookmarks ->
                _bookmarks.value = bookmarks
            }
        }
    }

    // 북마크 추가
    fun addBookmark(bookmarkEntity: BookmarkEntity) {
        viewModelScope.launch {
            bookmarkDao.insertBookmark(bookmarkEntity) // 수정된 부분
            loadBookmarks() // 추가 후 리스트 갱신
        }
    }

    // 북마크 삭제
    fun deleteBookmark(bookmarkEntity: BookmarkEntity) {
        viewModelScope.launch {
            bookmarkDao.deleteBookmark(bookmarkEntity) // 수정된 부분
            loadBookmarks() // 삭제 후 리스트 갱신
        }
    }
}
