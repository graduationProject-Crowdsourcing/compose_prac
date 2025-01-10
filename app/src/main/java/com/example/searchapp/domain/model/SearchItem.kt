package com.example.searchapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Parcelable은 SearchApp에서 activity간 데이터 전달을 위해 사용

sealed interface SearchItem : Parcelable {
    val id: String
    val title: String?
    val bookmarked : Boolean
    val date: String?

    @Parcelize
    data class ImageItem(
        override val id: String,
        override val title: String?,
        val thumbnail: String?,
        override val date: String?,
        override val bookmarked: Boolean = false
    ) : SearchItem

    @Parcelize
    data class VideoItem(
        override val id: String,
        override val title: String?,
        val thumbnail: String?,
        override val date: String?,
        override val bookmarked: Boolean = false
    ) : SearchItem
}