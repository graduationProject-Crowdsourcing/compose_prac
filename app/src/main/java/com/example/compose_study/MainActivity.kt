package com.example.compose_study

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.compose_study.ui.SearchApp
import com.example.compose_study.ui.theme.Compose_studyTheme
import com.kakao.sdk.common.util.Utility

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var keyHash = Utility.getKeyHash(this)
        Log.e("keyHash", keyHash)
        setContent {
            val navController = rememberNavController()

            Compose_studyTheme {
                Surface (modifier = Modifier.fillMaxSize().systemBarsPadding()) {
                    SearchApp(navController)
                }
            }
        }
    }
}