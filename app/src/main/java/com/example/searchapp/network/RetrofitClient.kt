package com.example.searchapp.network

import android.os.Build
import android.util.Log
import com.example.searchapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://dapi.kakao.com/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY  // network log 출력 level 설정
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor {
            chain ->
            val apikey = BuildConfig.KAKAO_API_KEY
            Log.d("RetrofitClient", "API Key Used: $apikey") // 디버깅 로그 추가
            val newRequest = chain.request().newBuilder()
                .addHeader(
                    "Authorization",
                    "KakaoAK $apikey")
                .build()
            chain.proceed(newRequest)
        }
        .build()

    fun create() : SearchApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(SearchApiService::class.java)
    }
}