package com.example.searchapp.bookmark

import androidx.room.Entity
import androidx.room.PrimaryKey

// bookmark data를 저장관리 하기 위한 data 구조
@Entity(tableName = "bookmark")
data class BookmarkEntity(
    @PrimaryKey val id : String,
    val title : String?,
    val thumbnail : String?,
    val date : String?,
    val isBookmarked : Boolean = true,
    val userId : String
)
