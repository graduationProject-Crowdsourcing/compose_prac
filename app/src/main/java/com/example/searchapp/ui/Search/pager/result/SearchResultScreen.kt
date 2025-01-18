package com.example.searchapp.ui.Search.pager.result

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.searchapp.ui.Search.pager.bookmark.BookmarkViewModel
import com.example.searchapp.domain.model.SearchItem
import com.example.searchapp.ui.Search.SearchViewModel
import com.example.searchapp.ui.Search.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultScreen(
    searchViewModel: SearchViewModel,
    bookmarkViewModel: BookmarkViewModel,
    onBackClick : () -> Unit,
    onItemClick : (SearchItem) -> Unit,
    onToggleBookmark: (SearchItem) -> Unit
){
    val uiState by searchViewModel.searchResults.observeAsState(UiState())

    if (uiState.isLoading){
        CircularProgressIndicator()
    } else{
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(uiState.searchItem){
                    item ->
                when (item) {
                    is SearchItem.ImageItem -> ImageResultItem(item = item, onItemClick, onToggleBookmark)
                    is SearchItem.VideoItem -> VideoResultItem(item = item, onItemClick, onToggleBookmark)
                }
            }
        }
    }
}



@Composable
fun ImageResultItem(
    item : SearchItem.ImageItem,
    onItemClick: (SearchItem) -> Unit,
    onToggleBookmark: (SearchItem) -> Unit){
    Row (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .clickable { onItemClick(item) },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        AsyncImage(
            model = item.thumbnail,
            contentDescription = "thumbnail",
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit
        )
        
        Spacer(modifier = Modifier.width(32.dp))

        // 제목 텍스트
        Text(
            text = item.title ?: "제목 없음",
            modifier = Modifier.weight(1f)

        )

        Spacer(modifier = Modifier.width(16.dp))

        IconButton(onClick = {onToggleBookmark(item)}) {
            Icon(
                imageVector = if (item.bookmarked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "bookmark_toggle"
            )
        }
    }
}

@Composable
fun VideoResultItem(
    item : SearchItem.VideoItem,
    onItemClick: (SearchItem) -> Unit,
    onToggleBookmark: (SearchItem) -> Unit){
    Row (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .clickable { onItemClick(item) },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        AsyncImage(
            model = item.thumbnail,
            contentDescription = "thumbnail",
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.width(32.dp))

        // 제목 텍스트
        Text(
            text = item.title ?: "제목 없음",
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        IconButton(
            onClick = {onToggleBookmark(item)},
            modifier = Modifier.padding(start = 8.dp)) {
            Icon(
                imageVector = if (item.bookmarked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "bookmark_toggle"
            )
        }
    }
}

