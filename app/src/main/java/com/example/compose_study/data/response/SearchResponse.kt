package com.example.compose_study.data.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class SearchResponse<T>(
    @SerializedName("meta") val meta: Meta,
    @SerializedName("documents") val documents: List<T>
)

data class Meta(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("pageable_count") val pageableCount: Int,
    @SerializedName("is_end") val isEnd: Boolean
)

data class ImageDocumentResponse(
    @SerializedName("collection") val collection: String,
    @SerializedName("thumbnail_url") val thumbnailUrl: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("display_sitename") val displaySitename: String,
    @SerializedName("doc_url") val docUrl: String,
    @SerializedName("datetime") val datetime: Date
)

data class VideoDocumentResponse(
    @SerializedName("title") val title: String,
    @SerializedName("play_time") val playTime: Int,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("url") val url: String,
    @SerializedName("datetime") val datetime: Date,
    @SerializedName("author") val author: String
)