package com.jihyun.compose_study.domain.repository

import com.jihyun.compose_study.domain.model.Bookmark

interface BookmarkRepository {
    suspend fun getAllBookmarks(): List<Bookmark>
    suspend fun addBookmark(bookmark: Bookmark)
    suspend fun deleteBookmark(bookmark: Bookmark)
}
