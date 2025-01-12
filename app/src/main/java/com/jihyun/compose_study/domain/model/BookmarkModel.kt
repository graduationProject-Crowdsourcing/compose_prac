package com.jihyun.compose_study.domain.model

data class BookmarkModel(
    val id: Int,
    val title: String,
    val url: String,
    val type: String // IMAGE 또는 VIDEO
)
