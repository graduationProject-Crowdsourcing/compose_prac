package com.jihyun.compose_study.data.database.entity

// 이미지 데이터 클래스
data class ImageItem(
    val thumbnail_url: String,
    val image_url: String,
    val display_sitename: String,
    val doc_url: String
)

// 동영상 데이터 클래스
data class VideoItem(
    val title: String,
    val url: String,
    val play_time: Int,
    val thumbnail: String,
    val author: String,
//    val display_sitename: String - 일단 지움 ..
)

// 응답 전체를 담는 데이터 클래스
data class KakaoResponse<T>(
    val documents: List<T>, // 이미지 또는 동영상 데이터를 담을 수 있도록 제네릭 타입으로 변경
    val meta: MetaData
)

// 메타데이터 클래스
data class MetaData(
    val total_count: Int,
    val pageable_count: Int,
    val is_end: Boolean
)
