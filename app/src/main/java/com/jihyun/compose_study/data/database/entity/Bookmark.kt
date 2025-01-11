package com.jihyun.compose_study.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
data class Bookmark(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // 자동 생성 ID
    val title: String,
    val url: String,
    val type: String // IMAGE 또는 VIDEO
)
