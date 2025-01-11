package com.example.compose_study

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_study.domain.usecase.SearchUseCase
import com.example.compose_study.domain.usecase.UseCaseFactory
import com.example.compose_study.local.BookmarkDao
import com.example.compose_study.local.BookmarkDatabase
import com.example.compose_study.local.BookmarkEntity
import com.example.compose_study.local.DatabaseProvider
import com.example.compose_study.network.RetrofitClient
import com.example.compose_study.ui.Search.SearchList
import com.example.compose_study.ui.Search.SearchListItem
import com.example.compose_study.ui.util.convertStringToDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {
    private val _uiState = mutableStateOf<UiState>(UiState())
    val uiState: State<UiState> = _uiState

    private val searchService = RetrofitClient.searchService

    private val bookmarkDao: BookmarkDao =
        DatabaseProvider.getDatabase(GlobalApplication.context).bookmarkDao()

    fun onSearch(query: String) = viewModelScope.launch {
        runCatching {
            showLoading(true)
            val items = createItems(query)

            _uiState.value = uiState.value.copy(
                searchList = items
            )
        }.onFailure {
            Log.e("jess", it.message.toString())
            showLoading(false)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        _uiState.value = uiState.value.copy(isLoading = isLoading)
    }

    private suspend fun createItems(query: String): List<SearchListItem> =
        searchUseCase.createListItem(query).map {
            SearchListItem(
                id = it.id,
                title = it.title,
                date = it.date,
                thumbnail = it.thumbnail
            )
        }


    fun setNickName(nickName: String) {
        _uiState.value = _uiState.value.copy(
            nickName = nickName
        )
    }

    fun insertBookmark(bookmarkEntity: BookmarkEntity) = viewModelScope.launch {
        try {
            bookmarkDao.insertBookmark(bookmarkEntity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteBookmark(bookmarkEntity: BookmarkEntity) = viewModelScope.launch {
        try {
            bookmarkDao.deleteBookmark(bookmarkEntity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getBookmark(nickName: String) = viewModelScope.launch {
        bookmarkDao.getBookmarksByNickname(nickName).collect { bookmarkList ->
            val updatedSearchList = _uiState.value.searchList.map { searchItem ->
                val isBookmarked = bookmarkList.any { it.title == searchItem.title && it.thumbnail == searchItem.thumbnail }
                searchItem.copy(bookmarked = isBookmarked)
            }

            _uiState.value = _uiState.value.copy(
                searchList = updatedSearchList,
                bookmarkList = bookmarkList.map {
                    SearchListItem(
                        id = it.id,
                        title = it.title,
                        bookmarked = true,
                        date = convertStringToDate(it.date),
                        thumbnail = it.thumbnail
                    )
                }
            )
        }
    }


    data class UiState(
        val searchList: List<SearchListItem> = emptyList(),
        val bookmarkList: List<SearchListItem> = emptyList(),
        val isLoading: Boolean = false,
        val nickName: String? = null
    )
}