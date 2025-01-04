package com.example.searchapp.ui.Search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import coil.compose.AsyncImage
import com.example.searchapp.data.SearchViewModel
import com.example.searchapp.data.SearchItem
import com.example.searchapp.data.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultScreen(
    searchViewModel: SearchViewModel,
    onBackClick : () -> Unit,
    onItemClick : (SearchItem) -> Unit
){
    val uiState by searchViewModel.searchResults.observeAsState(UiState())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "검색 결과")},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                    }
                }
                )
        }
    ) {
        paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                            is SearchItem.ImageItem -> ImageResultItem(item = item, onItemClick)
                            is SearchItem.VideoItem -> VideoResultItem(item = item, onItemClick)
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun ImageResultItem(
    item : SearchItem.ImageItem,
    onItemClick: (SearchItem) -> Unit){
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
            text = item.title ?: "제목 없음"
        )
    }
}

@Composable
fun VideoResultItem(
    item : SearchItem.VideoItem,
    onItemClick: (SearchItem) -> Unit){
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
            text = item.title ?: "제목 없음"
        )
    }
}

