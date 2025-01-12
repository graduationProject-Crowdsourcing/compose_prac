package com.jihyun.compose_study.domain.repository

import com.jihyun.compose_study.domain.model.BookmarkModel
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    suspend fun getAllBookmarks(): Flow<List<BookmarkModel>>
    suspend fun addBookmark(bookmark: BookmarkModel)
    suspend fun deleteBookmark(bookmark: BookmarkModel)
}
