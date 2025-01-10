package com.example.searchapp.domain.model

import com.example.searchapp.data.response.Meta
import com.google.gson.annotations.SerializedName

data class SearchEntity<T>(
    val documents: List<T>, // 제네릭
    val meta: MetaEntity
)


data class MetaEntity(
    val totalCount: Int,
    val pageableCount: Int,
    val isEnd: Boolean
)



data class ImageResponseEntity(
    val thumbnail_url: String,
    val datetime: String,
    val display_sitename: String,
    val collection: String,
    val image_url: String,
    val doc_url: String,
    val width: Int,
    val height: Int
)

data class VideoResponseEntity(
    val thumbnail: String,
    val datetime: String,
    val author: String,
    val url : String,
    val title: String,
    val play_time: Int
)

data class SearchItemEntity (
    val id: String,
    val title: String?,
    val bookmarked: Boolean,
    val date: String?
)

