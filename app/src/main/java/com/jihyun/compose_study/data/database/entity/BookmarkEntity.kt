package com.jihyun.compose_study.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jihyun.compose_study.domain.model.BookmarkModel

@Entity(tableName = "bookmark")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // 자동 생성 ID
    val title: String,
    val url: String,
    val type: String // IMAGE 또는 VIDEO
)

// BookmarkEntity -> BookmarkModel 변환 함수
fun BookmarkEntity.toDomainModel(): BookmarkModel {
    return BookmarkModel(
        id = this.id,
        title = this.title,
        url = this.url,
        type = this.type
    )
}

// BookmarkModel -> BookmarkEntity 변환 함수
fun BookmarkModel.toEntity(): BookmarkEntity {
    return BookmarkEntity(
        id = this.id,
        title = this.title,
        url = this.url,
        type = this.type
    )
}
