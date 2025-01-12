package com.jihyun.compose_study.data.repository

import com.jihyun.compose_study.data.database.dao.BookmarkDao
import com.jihyun.compose_study.data.database.entity.toDomainModel
import com.jihyun.compose_study.data.database.entity.toEntity
import com.jihyun.compose_study.data.datasource.BookmarkDataSource
import com.jihyun.compose_study.domain.model.BookmarkModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookmarkDataSourceImpl(
    private val bookmarkDao: BookmarkDao
) : BookmarkDataSource {
    override fun getAllBookmarks(): Flow<List<BookmarkModel>> {
        return bookmarkDao.getAllBookmarks().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun addBookmark(bookmark: BookmarkModel) {
        bookmarkDao.insertBookmark(bookmark.toEntity())
    }

    override suspend fun deleteBookmark(bookmark: BookmarkModel) {
        bookmarkDao.deleteBookmark(bookmark.toEntity())
    }
}
