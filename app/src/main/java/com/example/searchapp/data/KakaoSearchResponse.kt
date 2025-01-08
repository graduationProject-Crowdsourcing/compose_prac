package com.example.searchapp.data


import com.google.gson.annotations.SerializedName

// SerializedName => field 이름과 매핑하기 위해 사용, 여기서는 설정만 해두고 나중에 수정할지도?

data class KakaoSearchResponse<T>(
    @SerializedName("documents") val documents: List<T>, // 제네릭
    @SerializedName("meta") val meta: Meta
)


data class Meta(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("pageable_count") val pageableCount: Int,
    @SerializedName("is_end") val isEnd: Boolean
)



data class ImageResponse(
    @SerializedName("thumbnail_url") val thumbnail_url: String,
    @SerializedName("datetime") val datetime: String,
    @SerializedName("display_sitename") val display_sitename: String,
    @SerializedName("collection") val collection: String,
    @SerializedName("image_url") val image_url: String,
    @SerializedName("doc_url") val doc_url: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int
)

data class VideoResponse(
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("datetime") val datetime: String,
    @SerializedName("author") val author: String,
    @SerializedName("url") val url : String,
    @SerializedName("title") val title: String,
    @SerializedName("play_time") val play_time: Int
)

