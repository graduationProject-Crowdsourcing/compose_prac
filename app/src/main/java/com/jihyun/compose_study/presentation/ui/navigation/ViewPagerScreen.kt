package com.jihyun.compose_study.presentation.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.jihyun.compose_study.presentation.ui.media.MediaScreen
import com.jihyun.compose_study.presentation.viewmodel.BookmarkViewModel
import com.jihyun.compose_study.presentation.viewmodel.MediaViewModel
import kotlinx.coroutines.launch
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun ViewPagerScreen(
    mediaViewModel: MediaViewModel,
    bookmarkViewModel: BookmarkViewModel
) {
    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope() // 코루틴 스코프 선언

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
        ) {
            listOf("미디어", "북마크").forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch { // 코루틴 스코프 사용
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(title) }
                )
            }
        }

        HorizontalPager(
            count = 2, // 페이지 수
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> MediaScreen(mediaViewModel)
                1 -> BookmarkScreen(bookmarkViewModel)
            }
        }
    }
}

