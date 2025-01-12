package com.jihyun.compose_study.domain.usecase

import com.jihyun.compose_study.domain.model.BookmarkModel
import com.jihyun.compose_study.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow

class GetBookmarksUseCase(private val repository: BookmarkRepository) {
    suspend operator fun invoke(): Flow<List<BookmarkModel>> {
        return repository.getAllBookmarks()
    }
}
