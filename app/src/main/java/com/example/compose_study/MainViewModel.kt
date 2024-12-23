package com.example.compose_study

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_study.network.RetrofitClient
import com.example.compose_study.ui.Search.SearchListItem
import kotlinx.coroutines.launch
import java.util.UUID

class MainViewModel: ViewModel(){
    private val _uiState = mutableStateOf<UiState>(UiState())
    val uiState: State<UiState> = _uiState

    private val searchService = RetrofitClient.searchService

    fun onSearch(query:String) = viewModelScope.launch{
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

    private fun showLoading(isLoading: Boolean){
        _uiState.value = uiState.value.copy(isLoading = isLoading)
    }

    private suspend fun createItems(query: String): List<SearchListItem>{
        val imageItems = searchService.getSearchImage(query = query).documents.map {
            SearchListItem.ImageItem(
                id = UUID.randomUUID().toString(),
                title = it.displaySitename,
                thumbnail = it.thumbnailUrl,
                date = it.datetime
            )
        }
        val videoItems = searchService.getSearchVideo(query = query).documents.map {
            SearchListItem.VideoItem(
                id = UUID.randomUUID().toString(),
                title = it.title,
                thumbnail = it.thumbnail,
                date = it.datetime
            )
        }

        return arrayListOf<SearchListItem>().apply {
            addAll(imageItems)
            addAll(videoItems)
        }.sortedByDescending {
            it.date
        }
    }


    data class UiState(
        val searchList: List<SearchListItem> = emptyList(),
        val bookmarkList: List<SearchListItem> = emptyList(),
        val isLoading: Boolean = false
    )
}