package com.jihyun.compose_study

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.jihyun.compose_study.data.database.DatabaseProvider
import com.jihyun.compose_study.data.database.entity.BookmarkEntity
import com.jihyun.compose_study.presentation.ui.navigation.ViewPagerScreen
import com.jihyun.compose_study.presentation.viewmodel.BookmarkViewModel
import com.jihyun.compose_study.presentation.viewmodel.BookmarkViewModelFactory
import com.jihyun.compose_study.presentation.viewmodel.MediaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewModels 초기화
        val mediaViewModel: MediaViewModel by viewModels()

        // Room 데이터베이스 초기화
        val database = DatabaseProvider.getDatabase(applicationContext)

        val bookmarkViewModel: BookmarkViewModel by viewModels {
            BookmarkViewModelFactory(database.bookmarkDao())
        }

        setContent {
            MediaListScreen(mediaViewModel, bookmarkViewModel)
            ViewPagerScreen(mediaViewModel, bookmarkViewModel)
        }
    }
}

@Composable
fun MediaListScreen(mediaViewModel: MediaViewModel, bookmarkViewModel: BookmarkViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        SearchBar { query ->
            mediaViewModel.fetchMedia(query)
            mediaViewModel.fetchVideos(query)
        }

        val imageItems by mediaViewModel.imageItems.collectAsState()
        val videoItems by mediaViewModel.videoItems.collectAsState()

        if (imageItems.isNullOrEmpty() && videoItems.isNullOrEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "로딩 중입니다...")
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                imageItems?.let { items ->
                    items(items.size) { index ->
                        val item = items[index]
                        MediaItemView(
                            thumbnailUrl = item.thumbnail_url,
                            displaySite = item.display_sitename ?: "출처 없음",
                            onBookmarkClick = {
                                bookmarkViewModel.addBookmark(
                                    BookmarkEntity(
                                        id = 0, // Auto-generated
                                        title = item.display_sitename ?: "제목 없음",
                                        url = item.thumbnail_url,
                                        type = "IMAGE"
                                    )
                                )
                            }
                        )
                    }
                }

                videoItems?.let { items ->
                    items(items.size) { index ->
                        val item = items[index]
                        VideoItemView(
                            videoUrl = item.url,
                            videoTitle = item.title ?: item.author ?: "출처 없음",
                            onBookmarkClick = {
                                bookmarkViewModel.addBookmark(
                                    BookmarkEntity(
                                        id = 0,
                                        title = item.title ?: "제목 없음",
                                        url = item.url ?: "",
                                        type = "VIDEO"
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MediaItemView(
    thumbnailUrl: String,
    displaySite: String,
    onBookmarkClick: () -> Unit
) {
    Row(modifier = Modifier.padding(8.dp)) {
        AsyncImage(
            model = thumbnailUrl,
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = displaySite)
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = onBookmarkClick) {
            Text(text = "북마크")
        }
    }
}



@Composable
fun VideoItemView(videoUrl: String?, videoTitle: String?, onBookmarkClick: () -> Unit) {
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

        Button(onClick = onBookmarkClick) {
            Text(text = "북마크 추가")
        }
    }
}


@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var query by remember { mutableStateOf("") }

    Row(modifier = Modifier.padding(8.dp)) {
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("검색어를 입력하세요") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { onSearch(query) }) {
            Text("검색")
        }
    }
}

@Composable
fun BookmarkListScreen(bookmarkViewModel: BookmarkViewModel) {
    val bookmarks by bookmarkViewModel.bookmarks.collectAsState() // Flow를 State로 변환

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(bookmarks.size) { index ->
            val bookmark = bookmarks[index]
            Row(modifier = Modifier.padding(8.dp)) {
                Text(text = bookmark.title)
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    bookmarkViewModel.deleteBookmark(bookmark) // 북마크 삭제
                }) {
                    Text(text = "삭제")
                }
            }
        }
    }
}


