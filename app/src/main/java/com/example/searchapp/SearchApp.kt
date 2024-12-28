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

        composable("searchresult") {
            SearchResultScreen(
                searchViewModel = viewModel,
                onBackClick = {
                navController.popBackStack()
                },
                navigateToImageDetail = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("imgitem", it)
                    navController.navigate("imagedetailscreen")
                },
                navigateToVideoDetail = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("vitem", it)
                    navController.navigate("videodetailscreen")
                })
        }

        composable("imagedetailscreen") {
            val imageItem = navController.previousBackStackEntry?.savedStateHandle?.
            get<SearchItem.ImageItem>("imgitem") ?: SearchItem.ImageItem("", "", "", "", "", "", 0,0)
            ImageDetailScreen(
                item = imageItem,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("videodetailscreen") {
            val videoItem = navController.previousBackStackEntry?.savedStateHandle?.
            get<SearchItem.VideoItem>("vitem") ?: SearchItem.VideoItem("", "", "", "", "", 0)
            VideoDetailScreen(
                item = videoItem,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


    }
}