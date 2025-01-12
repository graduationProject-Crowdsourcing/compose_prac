package com.jihyun.compose_study.domain.usecase

import com.jihyun.compose_study.domain.model.BookmarkModel
import com.jihyun.compose_study.domain.repository.BookmarkRepository

class GetBookmarksUseCase(private val repository: BookmarkRepository) {
    suspend operator fun invoke(): List<BookmarkModel> {
        return repository.getAllBookmarks()
    }
}
