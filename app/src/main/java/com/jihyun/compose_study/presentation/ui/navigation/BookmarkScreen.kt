package com.jihyun.compose_study.presentation.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jihyun.compose_study.presentation.viewmodel.BookmarkViewModel

@Composable
fun BookmarkScreen(bookmarkViewModel: BookmarkViewModel) {
    val bookmarks by bookmarkViewModel.bookmarks.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(bookmarks.size) { index ->
            val bookmark = bookmarks[index]
            Row(modifier = Modifier.padding(8.dp)) {
                Text(text = bookmark.title)
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { bookmarkViewModel.deleteBookmark(bookmark) }) {
                    Text(text = "삭제")
                }
            }
        }
    }
}
