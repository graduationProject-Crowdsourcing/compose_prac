package com.example.compose_study.ui.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.compose_study.MainViewModel
import com.example.compose_study.R

@Composable
fun LoginScreen(navigateToSearch: (nickName: String) -> Unit) {
    val context = LocalContext.current
    val kaKaoSocialLogin = KaKaoSocialLogin(
        context = context,
        onLoginSuccess = { nickname ->
            if (nickname != null) {
                navigateToSearch(nickname)
            } else {
                Toast.makeText(context, "닉네임 정보를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        },
        onError = { error ->
            Log.e("error", "로그인 실패: $error")
            Toast.makeText(context, "로그인 중 문제가 발생했습니다.", Toast.LENGTH_SHORT).show()
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.kakao_login_medium_wide),
            contentDescription = "Kakao Login Icon",
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clickable { kaKaoSocialLogin.kakaoLogin() },
        )
    }
}
