package com.example.searchapp.data.service

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


    /* 기존 구조의 문제점 : return 으로 인해 계속 retrofit 인스턴스가 생성됨 */
//    fun create() : SearchApiService {
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        return retrofit.create(SearchApiService::class.java)
//    }

    // 기존 계속해서 인스턴스가 생성되는 구조에서 Retrofit 인스턴스를 singleton으로 한번만 호출 ==> 이후 재사용
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // SearchApiService 인스턴스를 선언하여 singleton으로 단일 객체로 재사용
    val searchApiService : SearchApiService by lazy {
        retrofit.create(SearchApiService::class.java)
    }
}