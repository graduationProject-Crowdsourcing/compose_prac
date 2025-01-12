package com.jihyun.compose_study

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.jihyun.compose_study.data.repository.BookmarkDataSourceImpl
import com.jihyun.compose_study.data.repository.BookmarkRepositoryImpl
import com.jihyun.compose_study.data.database.DatabaseProvider
import com.jihyun.compose_study.domain.repository.BookmarkRepository
import com.jihyun.compose_study.domain.usecase.AddBookmarkUseCase
import com.jihyun.compose_study.domain.usecase.DeleteBookmarkUseCase
import com.jihyun.compose_study.domain.usecase.GetBookmarksUseCase
import com.jihyun.compose_study.presentation.ui.navigation.ViewPagerScreen
import com.jihyun.compose_study.presentation.viewmodel.BookmarkViewModel
import com.jihyun.compose_study.presentation.viewmodel.BookmarkViewModelFactory
import com.jihyun.compose_study.presentation.viewmodel.MediaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Room 데이터베이스 초기화
        val database = DatabaseProvider.getDatabase(applicationContext)

        // DataSource 초기화
        val bookmarkDataSource = BookmarkDataSourceImpl(database.bookmarkDao())

        // Repository 초기화
        val bookmarkRepository: BookmarkRepository = BookmarkRepositoryImpl(bookmarkDataSource)

        // UseCase 초기화
        val addBookmarkUseCase = AddBookmarkUseCase(bookmarkRepository)
        val deleteBookmarkUseCase = DeleteBookmarkUseCase(bookmarkRepository)
        val getBookmarksUseCase = GetBookmarksUseCase(bookmarkRepository)

        // ViewModels 초기화
        val mediaViewModel: MediaViewModel by viewModels()
        val bookmarkViewModel: BookmarkViewModel by viewModels {
            BookmarkViewModelFactory(
                addBookmarkUseCase = addBookmarkUseCase,
                deleteBookmarkUseCase = deleteBookmarkUseCase,
                getBookmarksUseCase = getBookmarksUseCase
            )
        }

        setContent {
            ViewPagerScreen(
                mediaViewModel = mediaViewModel,
                bookmarkViewModel = bookmarkViewModel
            )
        }
    }
}
