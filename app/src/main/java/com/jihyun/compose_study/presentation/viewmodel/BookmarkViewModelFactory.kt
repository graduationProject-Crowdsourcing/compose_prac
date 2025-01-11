package com.jihyun.compose_study.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jihyun.compose_study.data.database.dao.BookmarkDao

class BookmarkViewModelFactory(private val bookmarkDao: BookmarkDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookmarkViewModel(bookmarkDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
