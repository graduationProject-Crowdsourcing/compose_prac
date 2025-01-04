package com.example.searchapp.ui.Search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.searchapp.data.SearchItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailScreen(
    item : SearchItem,
    onBackClick : () -> Unit
){
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "세부정보") },
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (item) {
                is SearchItem.ImageItem -> ImageDetail(imageItem = item)
                is SearchItem.VideoItem -> VideoDetail(videoItem = item)
            }
        }
    }
}

@Composable
fun ImageDetail(
    imageItem : SearchItem.ImageItem
){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ){
        AsyncImage(
            model = imageItem.thumbnail,
            contentDescription = "thumbnail",
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(64.dp))

        // 제목 텍스트
        Text(
            text = "출처 : ${imageItem.title}"
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "생성일자 : ${imageItem.date}"
        )
    }
}


@Composable
fun VideoDetail(
    videoItem : SearchItem.VideoItem
){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ){
        AsyncImage(
            model = videoItem.thumbnail,
            contentDescription = "thumbnail",
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(64.dp))

        // 제목 텍스트
        Text(
            text = "동영상 제목 : ${videoItem.title}"
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "생성일자 : ${videoItem.date}"
        )
    }
}

