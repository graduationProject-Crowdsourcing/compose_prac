package com.example.searchapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class UserPreferences @Inject constructor(
    private val context : Context
) {
    private val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    private val _userId = MutableLiveData<String?>()
    val userId: LiveData<String?> get() = _userId

    init {
        _userId.value = sharedPreferences.getString("user_id", null)
    }


    fun setUserId(userId: String) {
        sharedPreferences.edit().putString("user_id", userId).apply()
        _userId.value = userId
    }
}