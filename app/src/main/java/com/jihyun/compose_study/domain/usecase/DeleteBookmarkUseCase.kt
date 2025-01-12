package com.jihyun.compose_study.domain.usecase

import com.jihyun.compose_study.domain.model.BookmarkModel
import com.jihyun.compose_study.domain.repository.BookmarkRepository

class DeleteBookmarkUseCase(private val repository: BookmarkRepository) {
    suspend operator fun invoke(bookmark: BookmarkModel) {
        repository.deleteBookmark(bookmark)
    }
}
