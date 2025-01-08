package com.example.searchapp.ui.login

import android.content.Context
import android.util.Log

import com.kakao.sdk.user.UserApiClient

class KakaoLoginManager(private val context: Context) {

    // kakaoLogin에서는 현재 카카오톡 app이 깔려있는지 && 연결 가능한지에 따라 카카오톡 앱을 통한 로그인 또는 계정을 통한 로그인 실행
    fun kakaoLogin(onSuccess: (String) -> Unit, onFailure: (String) -> Unit){
        Log.i("KakaoLoginManager.kakaoLogin", "로그인 시작")
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            Log.i("KakaoLoginManager.kakaoLogin", "카카오톡 앱을 통한 로그인 시도")
            loginWithKakaoTalk(onSuccess, onFailure)
        } else {
            Log.i("KakaoLoginManager.kakaoLogin", "카카오톡 앱 미설치 : 카카오 계정 로그인 시도")
            loginWithKakaoAccount(onSuccess, onFailure)
        }
    }

    // kakaotalk앱을 통한 로그인 시도 => 성공시 유저정보 fetch, 실패한 경우는 앱은 깔려있지만 비로그인 상태일 경우 handleLogin 처리
    private fun loginWithKakaoTalk(onSuccess: (String) -> Unit, onFailure: (String) -> Unit){
        UserApiClient.instance.loginWithKakaoTalk(context){
            token, error ->
            if (error != null) {
                Log.e("KakaoLoginManager.loginWithKakaoTalk", "카카오톡 앱은 설치되어 있으나 로그인 실패 : ${error.message}")
                handleLoginError(error, onSuccess, onFailure)
            }else if (token != null){
                Log.i("KakaoLoginManager.loginWithKakaoTalk", "카카오톡 앱을 통한 로그인 성공, token : ${token.accessToken}")
                fetchUserInfo(onSuccess, onFailure)
            }
        }
    }

    // 앱이 깔려있지 않은 경우 카카오 계정을 통한 로그인 => 성공시 유저정보 fetch, 실패시 계정 로그인 실패처리
    private fun loginWithKakaoAccount(onSuccess: (String) -> Unit, onFailure: (String) -> Unit){
        UserApiClient.instance.loginWithKakaoAccount(context){
            token, error ->
            if (error != null) {
                Log.e("KakaoLoginManager.loginWithKakaoAccount", "카카오톡 계정을 통한 로그인 실패 : ${error.message}")
                onFailure("Kakao 계정 로그인 실패 : ${error.message}")
            }else if (token != null){
                Log.i("KakaoLoginManager.loginWithKakaoAccount", "카카오톡 계정을 통한 로그인 성공, token : ${token.accessToken}")
                fetchUserInfo(onSuccess, onFailure)
            }
        }
    }

    // 유저 정보를 가져와서 반환함, 현재는 사용자 id와 email정보만을 Toast로 띄우기
    private fun fetchUserInfo(onSuccess: (String) -> Unit, onFailure: (String) -> Unit){
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("KakaoLoginManager.fetchUserInfo", " 사용자 정보 요청 실패 : ${error.message}")
                onFailure("사용자 정보 요청 실패 : ${error.message}")
            } else if (user != null){
                val userInfo = "사용자 ID : ${user.id}\n이메일 : ${user.kakaoAccount?.email ?: "이메일 정보 없음"}"
                Log.i("KakaoLoginManager.fetchUserInfo", " 사용자 정보 요청 성공 : ${userInfo}")
                onSuccess(userInfo)
            }
        }
    }

    // 1차 앱을 통한 로그인 시도 실패시 에러 처리 => 계정을 통한 로그인 시도
    private fun handleLoginError(error : Throwable, onSuccess: (String) -> Unit, onFailure: (String) -> Unit){
        if (error.message?.contains("KakaoTalk is installed but not connected to Kakao account") == true) {
            Log.i("kakaoLoginManager.handleLoginError", "카카오톡 앱은 깔려있으나 연결 불가 : 카카오톡 계정을 통한 로그인 시도")
            loginWithKakaoAccount(onSuccess, onFailure)
        } else {
            Log.e("kakaoLoginManager.handleLoginError", "카카오톡 로그인 실패 : ${error.message}")
            onFailure("카카오톡 로그인 실패: ${error.message}")
        }
    }
}

//fun KakaoLogin(context: Context, onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
//    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
//        // 카카오톡 앱을 통한 로그인
//        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
//            if (error != null) {
//                Log.e("KakaoLogin", "카카오톡 로그인 실패: ${error.message}")
//                // 카카오 계정 로그인으로 대체
//                if (error.message?.contains("KakaoTalk is installed but not connected to Kakao account") == true) {
//                    loginWithKakaoAccount(context, onSuccess, onFailure)
//                } else {
//                    onFailure("카카오톡 로그인 실패: ${error.message}")
//                }
//            } else if (token != null) {
//                Log.i("KakaoLogin", "카카오톡 로그인 성공: ${token.accessToken}")
//                fetchUserInfo(onSuccess, onFailure) // 사용자 정보 요청
//            }
//        }
//    } else {
//        // 카카오 계정 로그인
//        loginWithKakaoAccount(context, onSuccess, onFailure)
//    }
//}
//
//private fun loginWithKakaoAccount(context: Context, onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
//    UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
//        if (error != null) {
//            Log.e("KakaoLogin", "카카오 계정 로그인 실패: ${error.message}")
//            onFailure("카카오 계정 로그인 실패: ${error.message}")
//        } else if (token != null) {
//            Log.i("KakaoLogin", "카카오 계정 로그인 성공: ${token.accessToken}")
//            fetchUserInfo(onSuccess, onFailure) // 사용자 정보 요청
//        }
//    }
//}
//
//private fun fetchUserInfo(onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
//    UserApiClient.instance.me { user, error ->
//        if (error != null) {
//            Log.e("KakaoLogin", "사용자 정보 요청 실패: ${error.message}")
//            onFailure("사용자 정보 요청 실패: ${error.message}")
//        } else if (user != null) {
//            val userInfo = "사용자 ID: ${user.id}, 이메일: ${user.kakaoAccount?.email ?: "없음"}"
//            Log.i("KakaoLogin", "사용자 정보: $userInfo")
//            onSuccess(userInfo) // 사용자 정보를 성공 콜백으로 전달
//        }
//    }
//}



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