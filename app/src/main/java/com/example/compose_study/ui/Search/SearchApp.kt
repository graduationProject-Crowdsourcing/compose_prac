package com.example.compose_study.ui.Search

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose_study.MainViewModel

@Composable
fun SearchApp() {

    val viewModel: MainViewModel = viewModel()

    SearchListScreen(viewModel)
}