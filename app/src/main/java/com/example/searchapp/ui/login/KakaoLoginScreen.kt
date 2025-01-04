package com.example.searchapp.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.searchapp.ui.login.KakaoLogin

@Composable
fun LoginScreen(
    navigateToSearch : () -> Unit
){
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "로그인")

        Button(onClick = {
            KakaoLogin(
                context = context,
                onSuccess = { message ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    navigateToSearch()
                },
                onFailure = { error ->
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                })
        }) {
            Text(text = "카카오 로그인")
        }
    }
}