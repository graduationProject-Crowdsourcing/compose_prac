package com.example.compose_study.ui.Search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.example.compose_study.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.Switch
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchListScreen(viewModel: MainViewModel) {
    val coroutineScope = rememberCoroutineScope()

    val tabs = listOf("Search", "Bookmark")
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabs.size })

    val uiState by viewModel.uiState

    Column(modifier = Modifier.fillMaxSize()) {
        SearchRow(viewModel)

        Box(modifier = Modifier.fillMaxWidth().height(2.dp).background(Color.Black))

        CustomViewPager(tabs, pagerState, coroutineScope, uiState)
        }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomViewPager(
    tabs: List<String>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    uiState: MainViewModel.UiState
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { BasicText(title) }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            if (page == 0) {
                SearchList(uiState.searchList)
            } else if (page == 1) {
                SearchList(uiState.bookmarkList)
            }
        }
    }
}


@Composable
fun SearchList(
    searchList: List<SearchListItem>
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(searchList) { item ->

            var checked by remember { mutableStateOf(item.bookmarked) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                when (item) {
                    is SearchListItem.ImageItem -> {
                        Image(
                            painter = rememberAsyncImagePainter(item.thumbnail),
                            contentDescription = "Thumbnail",
                            modifier = Modifier
                                .size(100.dp)
                                .aspectRatio(1f)
                                .clip(
                                    RoundedCornerShape(16.dp)
                                )
                        )

                        Text(
                            text = "image\n" + item.title.toString(),
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .weight(1f)  // 텍스트가 Switch와 겹치지 않도록
                                .wrapContentHeight(),
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 12.sp
                        )


                    }

                    is SearchListItem.VideoItem -> {
                        Image(
                            painter = rememberAsyncImagePainter(item.thumbnail),
                            contentDescription = "Thumbnail",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(16.dp)
                                .aspectRatio(1f)
                                .clip(
                                    RoundedCornerShape(16.dp)
                                )
                        )

                        Text(
                            text = "video\n" + item.title.toString(),
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .weight(1f)  // 텍스트가 Switch와 겹치지 않도록
                                .wrapContentHeight(),
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 12.sp
                        )
                    }

                }

                Switch(
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                    }
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(Color.Black)
            )
        }
    }
}


@Composable
fun SearchRow(viewModel:MainViewModel){
    var searchQuery by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .padding(8.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart // 텍스트가 중앙 정렬되도록 설정
                ) {
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = "검색어를 입력하세요",
                            color = Color.Gray,
                            style = MaterialTheme.typography.body1
                        )
                    }
                    innerTextField() // 실제 입력 필드
                }
            }
        )

        Spacer(modifier = Modifier.width(8.dp)) // TextField와 Button 사이의 간격 추가

        Button(
            onClick = { viewModel.onSearch(searchQuery) },
            modifier = Modifier.height(56.dp)
        ) {
            Text("검색")
        }
    }
 }


@Preview
@Composable
fun PreviewSearchApp() {
    SearchListScreen(MainViewModel())
}