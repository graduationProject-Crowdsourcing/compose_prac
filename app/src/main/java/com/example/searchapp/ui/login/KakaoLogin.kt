package com.example.searchapp.ui.login

import android.content.Context
import android.util.Log

import com.kakao.sdk.user.UserApiClient


fun KakaoLogin(context: Context, onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        // 카카오톡 앱을 통한 로그인
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                Log.e("KakaoLogin", "카카오톡 로그인 실패: ${error.message}")
                // 카카오 계정 로그인으로 대체
                if (error.message?.contains("KakaoTalk is installed but not connected to Kakao account") == true) {
                    loginWithKakaoAccount(context, onSuccess, onFailure)
                } else {
                    onFailure("카카오톡 로그인 실패: ${error.message}")
                }
            } else if (token != null) {
                Log.i("KakaoLogin", "카카오톡 로그인 성공: ${token.accessToken}")
                fetchUserInfo(onSuccess, onFailure) // 사용자 정보 요청
            }
        }
    } else {
        // 카카오 계정 로그인
        loginWithKakaoAccount(context, onSuccess, onFailure)
    }
}

private fun loginWithKakaoAccount(context: Context, onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
    UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
        if (error != null) {
            Log.e("KakaoLogin", "카카오 계정 로그인 실패: ${error.message}")
            onFailure("카카오 계정 로그인 실패: ${error.message}")
        } else if (token != null) {
            Log.i("KakaoLogin", "카카오 계정 로그인 성공: ${token.accessToken}")
            fetchUserInfo(onSuccess, onFailure) // 사용자 정보 요청
        }
    }
}

private fun fetchUserInfo(onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
    UserApiClient.instance.me { user, error ->
        if (error != null) {
            Log.e("KakaoLogin", "사용자 정보 요청 실패: ${error.message}")
            onFailure("사용자 정보 요청 실패: ${error.message}")
        } else if (user != null) {
            val userInfo = "사용자 ID: ${user.id}, 이메일: ${user.kakaoAccount?.email ?: "없음"}"
            Log.i("KakaoLogin", "사용자 정보: $userInfo")
            onSuccess(userInfo) // 사용자 정보를 성공 콜백으로 전달
        }
    }
}



//@Composable
//fun fetchUserInfo(
//    context: Context,
//    onResult : (String) -> Unit)
//{
//    UserApiClient.instance.me { user, error ->
//        if(error != null){
//            // 실패처리
//            onResult("사용자 정보 요청 실패 : $error")
//        }
//        else if (user != null){
//            // 로그인 성공
//            onResult("사용자 정보 : ${user.id}, ${user.kakaoAccount?.email}")
//        }
//    }
//}