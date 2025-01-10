package com.example.searchapp.ui.viewmodel


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchapp.domain.model.BookmarkEntity
import com.example.searchapp.data.local.BookmarkDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.searchapp.UserPreferences
import com.example.searchapp.data.repository.BookmarkRepositoryImpl
import com.example.searchapp.domain.repository.BookmarkRepository
import com.kakao.sdk.common.KakaoSdk.init


// Room db 초기화를 위해 application의 context를 사용
@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val repository: BookmarkRepository,
    private val bookmarkDao: BookmarkDao,
    private val userPreferences: UserPreferences
) : ViewModel() {

    // 단일 인스턴스 선언한 DatabaseProvider에서 db 인스턴스를 가져옴 => 이 db에서 DAO를 통해 DB와 상호작용
//    private val db = DatabaseProvider.getDatabase(application)
//    private val bookmarkDao = db.bookmarkDao()

    private val _bookmarks = mutableStateOf<List<BookmarkEntity>>(emptyList())
    val bookmarks: List<BookmarkEntity> get() = _bookmarks.value

    // 현재 로그인 된 사용자 ID 관리
//    var currentUserId : String? get() = userPreferences.getUserId()

    init {
        userPreferences.userId.observeForever{
            userId ->
            if (userId != null){
                loadBookmarks(userId)
            }
        }
        viewModelScope.launch {
            // Repository 상태를 구독하여 ViewModel의 상태 업데이트
            repository.bookmarks.collect { updatedBookmarks ->
                _bookmarks.value = updatedBookmarks
            }
        }
    }

    fun setUserId(userId: String) {
        userPreferences.setUserId(userId) // User ID 저장
        loadBookmarks(userId) // 사용자 변경 시 북마크 로드
    }

    // Room db 작업은 coroutine을 통해 viewModelScope에서 비동기 실행
    fun loadBookmarks(userId : String){
        try {
                viewModelScope.launch {
                    repository.reloadBookmarks(userId)
                }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
//    fun addBookmark(bookmark: BookmarkEntity) {
//        viewModelScope.launch {
//            try {
//                val userId = userPreferences.userId.value ?: return@launch
//                bookmarkDao.insertBookmark(bookmark.copy(userId = userId))
//                loadBookmarks(userId)  // 업데이트 작업 적용
//            }
//            catch (e:Exception){
//                e.printStackTrace()
//            }
//        }
//    }
//
//    fun removeBookmark(bookmark: BookmarkEntity) {
//        viewModelScope.launch {
//            try {
//                val userId = userPreferences.userId.value ?: return@launch
//                bookmarkDao.deleteBookmark(bookmark.copy(userId = userId))
//                loadBookmarks(userId)  // 업데이트 작업 적용
//            }
//            catch (e:Exception){
//                e.printStackTrace()
//            }
//        }
//    }
    fun toggleBookmark(bookmark: BookmarkEntity) {
        viewModelScope.launch {
            if (bookmarks.contains(bookmark)) {
                repository.removeBookmark(bookmark)
            } else {
                repository.addBookmark(bookmark)
            }
        }
    }

}