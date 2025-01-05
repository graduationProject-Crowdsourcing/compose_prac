package com.example.searchapp.bookmark

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
data class BookmarkEntity(
    @PrimaryKey val id : String,
    val title : String?,
    val thumbnail : String?,
    val date : String?,
    val isBookmarked : Boolean = true
)
