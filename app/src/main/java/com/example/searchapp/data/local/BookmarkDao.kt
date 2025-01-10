package com.example.searchapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.searchapp.domain.model.BookmarkEntity

// db CRUD
@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark : BookmarkEntity)

    @Delete
    suspend fun deleteBookmark(bookmark : BookmarkEntity)

    @Query("SELECT * FROM bookmark")
    suspend fun getAllBookmark() : List<BookmarkEntity>

    @Query("SELECT * FROM bookmark WHERE userId = :userId")
    suspend fun getBookmarkByUser(userId : String): List<BookmarkEntity>
}