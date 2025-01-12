package com.jihyun.compose_study.presentation.ui.media

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.jihyun.compose_study.presentation.viewmodel.MediaViewModel

@Composable
fun MediaScreen(mediaViewModel: MediaViewModel) {
    val imageItems by mediaViewModel.imageItems.collectAsState()
    val videoItems by mediaViewModel.videoItems.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Search Bar
        var query by remember { mutableStateOf("") }

        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("검색어를 입력하세요") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                mediaViewModel.fetchMedia(query)
                mediaViewModel.fetchVideos(query)
            }) {
                Text(text = "검색")
            }
        }

        // LazyColumn for displaying items
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            imageItems?.let { items ->
                items(items.size) { index ->
                    val item = items[index]
                    MediaItemView(
                        thumbnailUrl = item.thumbnail_url,
                        displaySite = item.display_sitename ?: "출처 없음"
                    )
                }
            }

            videoItems?.let { items ->
                items(items.size) { index ->
                    val item = items[index]
                    VideoItemView(
                        videoUrl = item.url,
                        videoTitle = item.title ?: item.author ?: "제목 없음"
                    )
                }
            }
        }
    }
}

@Composable
fun MediaItemView(
    thumbnailUrl: String,
    displaySite: String
) {
    Row(modifier = Modifier.padding(8.dp)) {
        AsyncImage(
            model = thumbnailUrl,
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = displaySite)
    }
}

@Composable
fun VideoItemView(
    videoUrl: String?,
    videoTitle: String?
) {
    Column(modifier = Modifier.padding(8.dp)) {
        val title = videoTitle ?: "제목 없음"

        if (!videoUrl.isNullOrEmpty()) {
            AndroidView(
                factory = { context ->
                    com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView(context).apply {
                        addYouTubePlayerListener(object : com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer) {
                                val videoId = videoUrl.substringAfter("v=")
                                youTubePlayer.cueVideo(videoId, 0f)
                            }
                        })
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        } else {
            Text("유효한 동영상 URL이 없습니다.")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = title)
    }
}
