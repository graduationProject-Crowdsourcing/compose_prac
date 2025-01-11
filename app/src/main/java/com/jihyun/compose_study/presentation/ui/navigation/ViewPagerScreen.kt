package com.jihyun.compose_study.presentation.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.jihyun.compose_study.BookmarkListScreen
import com.jihyun.compose_study.MediaListScreen
import com.jihyun.compose_study.presentation.viewmodel.BookmarkViewModel
import com.jihyun.compose_study.presentation.viewmodel.MediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewPagerScreen(
    mediaViewModel: MediaViewModel,
    bookmarkViewModel: BookmarkViewModel
) {
    val pagerState = rememberPagerState(
        pageCount = { 2 }
    )
    val coroutineScope = rememberCoroutineScope() // 코루틴 생성

    Column(modifier = Modifier.fillMaxSize()) {
        // 탭 UI
        Row(modifier = Modifier.fillMaxWidth()) {
            listOf("Media List", "Bookmarks").forEachIndexed { index, title ->
                Button(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index) // 탭 클릭 시 페이지 이동
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = title)
                }
            }
        }

        // HorizontalPager를 통한 페이지 전환
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> MediaListScreen(
                    mediaViewModel = mediaViewModel,
                    bookmarkViewModel = bookmarkViewModel
                )
                1 -> BookmarkListScreen(bookmarkViewModel = bookmarkViewModel)
            }
        }
    }
}


