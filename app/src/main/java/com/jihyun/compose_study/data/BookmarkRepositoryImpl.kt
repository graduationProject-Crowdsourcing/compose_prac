package com.jihyun.compose_study.data

import com.jihyun.compose_study.data.database.dao.BookmarkDao
import com.jihyun.compose_study.data.database.entity.toDomainModel
import com.jihyun.compose_study.data.database.entity.toEntity
import com.jihyun.compose_study.domain.model.BookmarkModel
import com.jihyun.compose_study.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookmarkRepositoryImpl(private val bookmarkDao: BookmarkDao) : BookmarkRepository {

    override suspend fun getAllBookmarks(): Flow<List<BookmarkModel>> {
        return bookmarkDao.getAllBookmarks()
            .map { entities -> // Flow<List<BookmarkEntity>> -> Flow<List<BookmarkModel>>
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
