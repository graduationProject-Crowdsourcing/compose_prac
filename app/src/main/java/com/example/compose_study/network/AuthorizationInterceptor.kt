package com.example.compose_study.network

import com.example.compose_study.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor: Interceptor {
    override fun intercept(
        chain: Interceptor.Chain
    ): Response {
        val kakaoSearchApiKey = BuildConfig.KAKAO_SEARCH_KEY

        val newRequest = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "KakaoAK %s".format(kakaoSearchApiKey),
            ).build()

        return chain.proceed(newRequest)
    }
}