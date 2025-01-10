package com.example.searchapp.ui.Search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.searchapp.domain.model.BookmarkEntity
import com.example.searchapp.ui.viewmodel.BookmarkViewModel

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
                BookmarkItem(
                    bookmark = bookmark,
                    onToggleBookmark = {
                        viewModel.removeBookmark(bookmark)
                    })
            }
        }
    }
}

@Composable
fun BookmarkItem(
    bookmark: BookmarkEntity,
    onToggleBookmark : (BookmarkEntity) -> Unit
){

    Row (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        AsyncImage(
            model = bookmark.thumbnail,
            contentDescription = "thumbnail",
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.width(32.dp))

        // 제목 텍스트
        Text(
            text = bookmark.title ?: "제목 없음",
            modifier = Modifier.weight(1f)

        )

        Spacer(modifier = Modifier.width(16.dp))

        IconButton(
            onClick = { onToggleBookmark(bookmark) }
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "북마크 해제"
            )
        }
    }
}