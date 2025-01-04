package com.example.searchapp.ui.Search

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.searchapp.ui.login.LoginScreen
import com.example.searchapp.data.SearchViewModel
import com.example.searchapp.data.SearchItem


@Composable
fun SearchApp(){
    val viewModel : SearchViewModel = viewModel()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login"){
        composable("login") {
            LoginScreen(
                navigateToSearch = {
                    navController.navigate("search")
                }
            )
        }

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
                onItemClick = {
                    item ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("item", item)
                    navController.navigate("itemdetail")
                }
                )
        }
        
        composable("itemdetail"){
            val item = navController.previousBackStackEntry?.savedStateHandle?.get<SearchItem>("item")
            
            if (item != null){
                ItemDetailScreen(
                    item = item,
                    onBackClick = {navController.popBackStack()}
                ) 
            } else {
                Text(text = "item not found")
            }
        }

//        composable("imagedetailscreen") {
//            val imageItem = navController.previousBackStackEntry?.savedStateHandle?.
//            get<SearchItem.ImageItem>("imgitem") ?: SearchItem.ImageItem("", "", "", "", "", "", 0,0)
//            ImageDetailScreen(
//                item = imageItem,
//                onBackClick = {
//                    navController.popBackStack()
//                }
//            )
//        }
//
//        composable("videodetailscreen") {
//            val videoItem = navController.previousBackStackEntry?.savedStateHandle?.
//            get<SearchItem.VideoItem>("vitem") ?: SearchItem.VideoItem("", "", "", "", "", 0)
//            VideoDetailScreen(
//                item = videoItem,
//                onBackClick = {
//                    navController.popBackStack()
//                }
//            )
//        }


    }
}