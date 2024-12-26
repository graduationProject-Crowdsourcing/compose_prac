package com.example.searchapp

import com.google.gson.annotations.SerializedName
import java.time.OffsetDateTime

data class KakaoSearchResponse(
    val documents: List<Map<String, Any>>, // JSON 필드가 달라서 Map으로 처리
    val meta: Meta
)


data class Meta(
    val total_count : Int,
    val pageable_count : Int,
    val is_end : Boolean
)

//data class SearchItem(
//    val collection: String,
//    val thumbnail_url: String,
//    val image_url: String,
//    val width: Int,
//    val height: Int,
//    val display_sitename: String,
//    val doc_url: String,
//    val datetime: String
//)

//data class ImageItem(
//    val thumbnail_url: String,
//    val datetime: String,
//    val display_sitename: String,
//    val collection: String,
//    val image_url: String,
//    val doc_url: String,
//    val width: Int,
//    val height: Int
//)
//
//data class VideoItem(
//    val thumbnail: String,
//    val datetime: String,
//    val author: String,
//    val url : String,
//    val title: String,
//    val play_time: Int
//)


sealed class SearchItem {
    data class ImageItem(
        val thumbnail_url: String,
        val datetime: String,
        val display_sitename: String,
        val collection: String,
        val image_url: String,
        val doc_url: String,
        val width: Int,
        val height: Int
    ) : SearchItem()

    data class VideoItem(
        val thumbnail: String,
        val datetime: String,
        val author: String,
        val url : String,
        val title: String,
        val play_time: Int
    ) : SearchItem()
}
