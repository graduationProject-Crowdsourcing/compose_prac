package com.example.searchapp.domain.model

import com.example.searchapp.data.local.BookmarkEntity


fun SearchItem.toBookmarkEntity(userId: String): BookmarkEntity {
    return BookmarkEntity(
        id = this.id,
        title = this.title,
        thumbnail = when (this) {
            is SearchItem.ImageItem -> this.thumbnail
            is SearchItem.VideoItem -> this.thumbnail
        },
        date = this.date,
        userId = userId
    )
}

fun BookmarkEntity.toSearchItem(): SearchItem {
    return when {
        // 예제 조건에 맞게 분기 처리
        this.thumbnail?.endsWith(".jpg") ?: true || this.thumbnail?.endsWith(".png") ?: true -> {
            SearchItem.ImageItem(
                id = this.id,
                title = this.title,
                thumbnail = this.thumbnail,
                date = this.date,
                bookmarked = true
            )
        }
        else -> {
            SearchItem.VideoItem(
                id = this.id,
                title = this.title,
                thumbnail = this.thumbnail,
                date = this.date,
                bookmarked = true
            )
        }
    }
}
