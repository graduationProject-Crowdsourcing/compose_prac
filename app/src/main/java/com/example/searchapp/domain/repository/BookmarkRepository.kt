package com.example.searchapp.domain.repository

import com.example.searchapp.data.local.BookmarkEntity
import kotlinx.coroutines.flow.StateFlow

interface BookmarkRepository {
    val bookmarks: StateFlow<List<BookmarkEntity>>

    suspend fun getBookmarksByUser(userId : String): List<BookmarkEntity> // 북마크 리스트 가져오기
    suspend fun addBookmark(bookmark : BookmarkEntity)
    suspend fun removeBookmark(bookmark: BookmarkEntity)
    suspend fun reloadBookmarks(userId: String)
    suspend fun toggleBookmark(bookmark: BookmarkEntity)
}