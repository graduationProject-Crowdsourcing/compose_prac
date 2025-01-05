package com.example.searchapp.ui.Search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.searchapp.data.SearchItem
import com.example.searchapp.data.SearchViewModel
import com.example.searchapp.data.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    viewModel: SearchViewModel,
    onBackClick : () -> Unit
){
    val uiState by viewModel.searchResults.observeAsState(UiState())

    if (uiState.bookmarkList.isEmpty()){
        Text(
            text = "북마크 항목이 없음",
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center))
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ){
            items(uiState.bookmarkList) {
                    item ->
                BookmarkItem(item = item)
            }
        }
    }
}

@Composable
fun BookmarkItem(
    item: SearchItem
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.title ?: "제목없음",
            modifier = Modifier.weight(1f))
    }
}