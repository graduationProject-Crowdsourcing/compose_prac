package com.example.compose_study.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val id: String = "0",
    val nickname: String,
    val thumbnail: String,
    val title: String,
    val date: String
)
