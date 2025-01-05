package com.example.searchapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.searchapp.bookmark.BookmarkEntity
import com.example.searchapp.bookmark.DatabaseProvider
import kotlinx.coroutines.launch

// Room db 초기화를 위해 application의 context를 사용
class BookmarkViewModel(application: Application) : AndroidViewModel(application) {

    // 단일 인스턴스 선언한 DatabaseProvider에서 db 인스턴스를 가져옴 => 이 db에서 DAO를 통해 DB와 상호작용
    private val db = DatabaseProvider.getDatabase(application)
    private val bookmarkDao = db.bookmarkDao()

    // view model 내부에서 data(bookmark list)를 저장하고 수정하는 객체, LiveData를 통해 선언했는데 mutableStateOf해도 같음
    private val _bookmarks = MutableLiveData<List<BookmarkEntity>>()
    val bookmarks : LiveData<List<BookmarkEntity>> get() = _bookmarks

    // 현재 로그인 된 사용자 ID 관리
    var currentUserId : String? = null

    // Room db 작업은 coroutine을 통해 viewModelScope에서 비동기 실행

    fun loadBookmarks(){
        try {
            currentUserId?.let {
                userId ->
                viewModelScope.launch {
                    _bookmarks.value = bookmarkDao.getBookmarkByUser(userId)
                }
            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
    fun addBookmark(bookmark: BookmarkEntity) {
        viewModelScope.launch {
            try {
                currentUserId?.let { userId ->
                    viewModelScope.launch {
                        bookmarkDao.insertBookmark(bookmark.copy(userId = userId))
                        loadBookmarks()  // 업데이트 작업 적용
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun removeBookmark(bookmark: BookmarkEntity) {
        viewModelScope.launch {
            try {
                currentUserId?.let { userId ->
                    viewModelScope.launch {
                        bookmarkDao.deleteBookmark(bookmark.copy(userId = userId))
                        loadBookmarks()  // 업데이트 작업 적용
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun toggleBookmark(item: SearchItem) {
        currentUserId?.let { userId ->
            viewModelScope.launch {
                val bookmarkEntity = BookmarkEntity(
                    id = item.id,
                    title = item.title,
                    thumbnail = when (item) {
                        is SearchItem.ImageItem -> item.thumbnail
                        is SearchItem.VideoItem -> item.thumbnail
                    },
                    date = item.date,
                    isBookmarked = !item.bookmarked,
                    userId = userId
                )

                if (item.bookmarked) {
                    bookmarkDao.deleteBookmark(bookmarkEntity)
                } else {
                    bookmarkDao.insertBookmark(bookmarkEntity)
                }

                // 북마크 데이터를 다시 로드하여 UI에 반영
                loadBookmarks()
            }
        }
    }

}