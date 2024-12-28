package com.example.searchapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailScreen(
    item : SearchItem.ImageItem,
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
            ImageDetail(imageItem = item)
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
            model = imageItem.thumbnail_url,
            contentDescription = "thumbnail",
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(64.dp))

        // 제목 텍스트
        Text(
            text = "출처 : ${imageItem.display_sitename}"
        )

        Spacer(modifier = Modifier.height(8.dp))

        // collection
        Text(
            text = "이미지 종류 : ${imageItem.collection}"
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "생성일자 : ${imageItem.datetime}"
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "원본 사이트 : ${imageItem.doc_url}",
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoDetailScreen(
    item : SearchItem.VideoItem,
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
            VideoDetail(videoItem = item)
        }
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
            text = "동영상 제목 : ${videoItem.title} / ${videoItem.play_time}"
        )

        Spacer(modifier = Modifier.height(8.dp))

        // collection
        Text(
            text = "동영상 업로더 : ${videoItem.author}"
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "생성일자 : ${videoItem.datetime}"
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "원본 사이트 : ${videoItem.url}",
        )
    }
}

