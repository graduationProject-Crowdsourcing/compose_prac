package com.example.searchapp

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultScreen(
    searchViewModel: SearchViewModel,
    onBackClick : () -> Unit
){
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
        SearchResultList(
            searchList = searchViewModel.searchResults,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun SearchResultList(searchList: LiveData<List<SearchItem>>, modifier: Modifier){
    val searchItems by searchList.observeAsState(initial = emptyList())

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(searchItems){
            item ->
            when (item) {
                is SearchItem.ImageItem -> ImageResultItem(item = item)
                is SearchItem.VideoItem -> VideoResultItem(item = item)
            }
        }
    }
}

@Composable
fun ImageResultItem(item : SearchItem.ImageItem){
    Row (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        AsyncImage(
            model = item.thumbnail_url,
            contentDescription = "thumbnail",
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit
        )
        
        Spacer(modifier = Modifier.width(32.dp))

        // 제목 텍스트
        Text(
            text = item.display_sitename
        )
    }
}

@Composable
fun VideoResultItem(item : SearchItem.VideoItem){
    Row (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
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
            text = item.title
        )
    }
}

