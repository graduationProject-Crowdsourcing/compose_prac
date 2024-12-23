package com.example.compose_study.ui.Search

import java.util.Date

sealed interface SearchListItem {

    val id: String
    val title: String?
    val bookmarked: Boolean
    val date: Date?

    data class ImageItem(
        override val id: String,
        override val title: String?,
        val thumbnail: String?,
        override val date: Date?,
        override val bookmarked: Boolean = false,
    ) : SearchListItem

    data class VideoItem(
        override val id: String,
        override val title: String?,
        val thumbnail: String?,
        override val date: Date?,
        override val bookmarked: Boolean = false,
    ) : SearchListItem
}