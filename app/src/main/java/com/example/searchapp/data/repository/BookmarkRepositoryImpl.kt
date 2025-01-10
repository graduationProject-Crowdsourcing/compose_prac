package com.example.searchapp.data.repository

import com.example.searchapp.domain.model.BookmarkEntity
import com.example.searchapp.domain.repository.BookmarkRepository
import com.example.searchapp.data.local.BookmarkDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val dao: BookmarkDao
) : BookmarkRepository{
    override suspend fun getBookmarksByUser(userId: String): List<BookmarkEntity> {
        return dao.getBookmarkByUser(userId)
    }

    override suspend fun addBookmark(bookmark: BookmarkEntity) {
        dao.insertBookmark(bookmark)
    }

    override suspend fun removeBookmark(bookmark: BookmarkEntity) {
        dao.deleteBookmark(bookmark)
    }
}