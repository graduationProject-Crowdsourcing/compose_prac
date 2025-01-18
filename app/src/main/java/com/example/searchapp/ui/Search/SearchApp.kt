package com.example.searchapp.ui.Search

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.searchapp.ui.Search.pager.bookmark.BookmarkViewModel
import com.example.searchapp.ui.login.LoginScreen
import com.example.searchapp.domain.model.SearchItem
import com.example.searchapp.ui.Search.pager.result.ItemDetailScreen
import com.example.searchapp.ui.Search.SearchScreen
import com.example.searchapp.ui.Search.SearchViewModel
import com.example.searchapp.ui.Search.pager.SearchPager


@Composable
fun SearchApp(){
    val bookmarkViewModel: BookmarkViewModel = hiltViewModel()
    val viewModel: SearchViewModel = hiltViewModel()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login"){
        composable("login") {
            LoginScreen(
                bookmarkViewModel = bookmarkViewModel,
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
            SearchPager(
                bookmarkViewModel = bookmarkViewModel,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onItemClick = {
                    item ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("item", item)
                    navController.navigate("itemdetail")
                })
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
    }
}