package com.example.compose_study.domain.model

import com.example.compose_study.data.response.ImageDocumentResponse
import com.example.compose_study.data.response.Meta
import com.example.compose_study.data.response.SearchResponse
import com.example.compose_study.data.response.VideoDocumentResponse
import java.util.UUID

fun SearchResponse<ImageDocumentResponse>.toImageEntity() = SearchEntity<ImageDocumentEntity>(
    meta = meta?.toImageEntity(),
    documents = documents?.map {
        it.toImageEntity()
    } ?: emptyList()
)

fun SearchResponse<VideoDocumentResponse>.toVideoEntity() = SearchEntity<VideoDocumentEntity>(
    meta = meta?.toImageEntity(),
    documents = documents?.map {
        it.toImageEntity()
    } ?: emptyList()
)


fun Meta.toImageEntity() = MetaEntity(
    totalCount = totalCount,
    pageableCount = pageableCount,
    isEnd = isEnd,
)

fun ImageDocumentResponse.toImageEntity() = ImageDocumentEntity(
    id = UUID.randomUUID().toString(),
    collection = collection,
    thumbnailUrl = thumbnailUrl,
    imageUrl = imageUrl,
    width = width,
    height = height,
    displaySitename = displaySitename,
    docUrl = docUrl,
    datetime = datetime,
)

fun VideoDocumentResponse.toImageEntity() = VideoDocumentEntity(
    id = UUID.randomUUID().toString(),
    title = title,
    playTime = playTime,
    thumbnail = thumbnail,
    url = url,
    datetime = datetime,
    author = author
)