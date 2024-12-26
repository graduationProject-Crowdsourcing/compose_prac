package com.example.searchapp

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun SearchApp(){
    val viewModel : SearchViewModel = viewModel()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "search"){
        composable("search"){
            SearchScreen(viewModel = viewModel, onSearch = {
                navController.navigate("searchresult")
            })
        }

        // 챌린저 상위 10명 화면
        composable("searchresult") {
            SearchResultScreen(searchViewModel = viewModel, onBackClick = {
                navController.popBackStack()
            })
        }
    }
}