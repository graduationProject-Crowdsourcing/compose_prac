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
import com.example.searchapp.bookmark.BookmarkEntity
import com.example.searchapp.data.BookmarkViewModel
import com.example.searchapp.data.SearchItem
import com.example.searchapp.data.SearchViewModel
import com.example.searchapp.data.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel,
    onBackClick : () -> Unit
){
    val bookmarks by viewModel.bookmarks.observeAsState(emptyList())

    if (bookmarks.isEmpty()){
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
            items(bookmarks) {
                    bookmark ->
                BookmarkItem(bookmark = bookmark)
            }
        }
    }
}

@Composable
fun BookmarkItem(
    bookmark: BookmarkEntity
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = bookmark.title ?: "제목없음",
            modifier = Modifier.weight(1f))
    }
}