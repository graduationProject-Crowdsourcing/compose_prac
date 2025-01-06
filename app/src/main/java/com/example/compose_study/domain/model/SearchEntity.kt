package com.example.compose_study.domain.model

import java.util.Date


data class SearchEntity<T>(
    val meta: MetaEntity?,
    val documents: List<T>
)


data class MetaEntity(
    val totalCount: Int?,
    val pageableCount: Int?,
    val isEnd: Boolean?,
)

data class ImageDocumentEntity(
    val id: String,
    val collection: String?,
    val thumbnailUrl: String?,
    val imageUrl: String?,
    val width: Int?,
    val height: Int?,
    val displaySitename: String?,
    val docUrl: String?,
    val datetime: Date?,
)

data class VideoDocumentEntity(
    val id: String,
    val title: String,
    val playTime: Int,
    val thumbnail: String,
    val url: String,
    val datetime: Date,
    val author: String
)

data class SearchListEntity(
    val id: String,
    val title: String,
    val thumbnail: String,
    val date: Date
)