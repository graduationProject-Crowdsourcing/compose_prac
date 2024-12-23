package com.example.compose_study.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor: Interceptor {
    override fun intercept(
        chain: Interceptor.Chain
    ): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "KakaoAK %s".format("6489d07b286e32d75d8ac3a6fb8083bb"),
            ).build()

        return chain.proceed(newRequest)
    }
}