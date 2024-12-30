package com.example.compose_study.ui.Search

import java.util.Date

sealed interface SearchListItem {

    val id: String
    val title: String?
    val bookmarked: Boolean
    val date: Date
    val thumbnail: String?

    data class ImageItem(
        override val id: String,
        override val title: String?,
        override val thumbnail: String?,
        override val date: Date,
        override val bookmarked: Boolean = false,
    ) : SearchListItem

    data class VideoItem(
        override val id: String,
        override val title: String?,
        override val thumbnail: String?,
        override val date: Date,
        override val bookmarked: Boolean = false,
    ) : SearchListItem

    data class BookmarkItem(
        override val id: String,
        override val title: String?,
        override val bookmarked: Boolean = true,
        override val date: Date,
        override val thumbnail: String?
    ): SearchListItem
}