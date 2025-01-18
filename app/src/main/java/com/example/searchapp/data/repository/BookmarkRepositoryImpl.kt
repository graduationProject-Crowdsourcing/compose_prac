package com.example.searchapp.data.repository

import com.example.searchapp.data.local.BookmarkEntity
import com.example.searchapp.domain.repository.BookmarkRepository
import com.example.searchapp.data.local.BookmarkDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val dao: BookmarkDao
) : BookmarkRepository{

    private val _bookmarks = MutableStateFlow<List<BookmarkEntity>>(emptyList())
    override val bookmarks: StateFlow<List<BookmarkEntity>> get() = _bookmarks

    override suspend fun getBookmarksByUser(userId: String): List<BookmarkEntity> {
        return dao.getBookmarkByUser(userId)
    }

    override suspend fun addBookmark(bookmark: BookmarkEntity) {
        dao.insertBookmark(bookmark)
        reloadBookmarks(bookmark.userId)
    }

    override suspend fun removeBookmark(bookmark: BookmarkEntity) {
        dao.deleteBookmark(bookmark)
        reloadBookmarks(bookmark.userId)
    }

    override suspend fun reloadBookmarks(userId: String){
        val updateBookmarks = dao.getBookmarkByUser(userId)
        _bookmarks.value = updateBookmarks
    }

    override suspend fun toggleBookmark(bookmark: BookmarkEntity) {
        val isBookmarked = _bookmarks.value.any { it.id == bookmark.id }
        if (isBookmarked) {
            dao.deleteBookmark(bookmark) // 존재하면 해제
        } else {
            dao.insertBookmark(bookmark) // 존재하지 않으면 추가
        }
    }
}