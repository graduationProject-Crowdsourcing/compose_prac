package com.jihyun.compose_study.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jihyun.compose_study.domain.usecase.AddBookmarkUseCase
import com.jihyun.compose_study.domain.usecase.DeleteBookmarkUseCase
import com.jihyun.compose_study.domain.usecase.GetBookmarksUseCase

class BookmarkViewModelFactory(
    private val getBookmarksUseCase: GetBookmarksUseCase,
    private val addBookmarkUseCase: AddBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookmarkViewModel(
                getBookmarksUseCase,
                addBookmarkUseCase,
                deleteBookmarkUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
