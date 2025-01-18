import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

// apikeys.properties 파일 읽기
val apikeysPropertiesFile = rootProject.file("apikeys.properties")
val apikeysProperties = Properties()

if (apikeysPropertiesFile.exists()) {
    apikeysProperties.load(FileInputStream(apikeysPropertiesFile))
}

android {
    namespace = "com.example.searchapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.searchapp"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildFeatures {
            buildConfig = true
            compose = true
        }

        // BuildConfig에 API 키 추가
        buildConfigField(
            "String",
            "KAKAO_API_KEY",
            "\"${apikeysProperties.getProperty("KAKAO_API_KEY") ?: ""}\""
        )
        buildConfigField(
            "String",
            "KAKAO_NATIVE_API_KEY",
            "\"${apikeysProperties.getProperty("KAKAO_NATIVE_API_KEY") ?: ""}\""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        // BOM에 맞춰서 설정
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    // 필요 시 원하는 빌드 툴 버전 지정
    buildToolsVersion = "35.0.0"
}

dependencies {
    // Compose BOM
    implementation(platform(libs.compose.bom))

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // OkHttp
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // RecyclerView
    implementation(libs.recyclerview)

    // Compose
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)

    // Lifecycle, ViewModel, LiveData
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.compose.runtime.livedata)

    // Navigation
    implementation(libs.navigation.compose)

    // Coil
    implementation(libs.coil.compose)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    // Kakao
    implementation(libs.kakao.sdk.user)

    // Accompanist Pager
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Hilt Navigation Compose
    implementation(libs.hilt.navigation.compose)

    // Core KTX, Activity Compose
    implementation(libs.core.ktx)
    implementation(libs.activity.compose)

    // 테스트
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)
    // Compose 테스트에 BOM 사용
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)

    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
}
