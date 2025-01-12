package com.jihyun.compose_study.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://dapi.kakao.com/"

    // OkHttpClient 빌더에 Interceptor 추가
    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor()) // Interceptor 추가
        .build()

    // Retrofit 빌더에 OkHttpClient 적용
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client) // client 연결
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
