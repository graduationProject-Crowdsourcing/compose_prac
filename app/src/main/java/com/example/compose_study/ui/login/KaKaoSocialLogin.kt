package com.example.compose_study.ui.login

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class KaKaoSocialLogin(
    private val context: Context,
    private val onLoginSuccess: (nickname: String?) -> Unit,
    private val onError: (Throwable?) -> Unit
) {

    fun kakaoLogin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            // 카카오톡 로그인
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                handleLoginResult(token, error)
            }
        } else {
            // 카카오 계정 로그인
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                handleLoginResult(token, error)
            }
        }
    }

    private fun handleLoginResult(token: OAuthToken?, error: Throwable?) {
        if (error != null) {
            onError(error)
        } else if (token != null) {
            UserApiClient.instance.me { user, meError ->
                if (meError != null) {
                    onError(meError)
                } else if (user != null) {
                    val nickname = user.kakaoAccount?.profile?.nickname
                    onLoginSuccess(nickname)
                } else {
                    onLoginSuccess(null)
                }
            }
        }
    }
}
