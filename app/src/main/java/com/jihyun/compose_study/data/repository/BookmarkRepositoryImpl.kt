package com.jihyun.compose_study.data.repository

import com.jihyun.compose_study.data.datasource.BookmarkDataSource
import com.jihyun.compose_study.domain.model.BookmarkModel
import com.jihyun.compose_study.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow

class BookmarkRepositoryImpl(
    private val dataSource: BookmarkDataSource // BookmarkDataSource 사용
) : BookmarkRepository {

    override suspend fun getAllBookmarks(): Flow<List<BookmarkModel>> {
        return dataSource.getAllBookmarks() // BookmarkDataSource 활용
    }

    override suspend fun addBookmark(bookmark: BookmarkModel) {
        dataSource.addBookmark(bookmark) // BookmarkDataSource 활용
    }

    override suspend fun deleteBookmark(bookmark: BookmarkModel) {
        dataSource.deleteBookmark(bookmark) // BookmarkDataSource 활용
    }
}
