package com.example.compose_study.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.compose_study.MainViewModel
import com.example.compose_study.ui.Search.SearchListScreen
import com.example.compose_study.ui.login.LoginScreen

@Composable
fun SearchApp(navController: NavHostController) {
    val viewModel: MainViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen( navigateToSearch = { nickName ->

                viewModel.setNickName(nickName = nickName)
                navController.navigate(Screen.SearchScreen.route)
            })
        }

        composable(route = Screen.SearchScreen.route) {
            SearchListScreen(viewModel)
        }
    }
}


sealed class Screen(val route:String) {
    object LoginScreen:Screen("loginScreen")
    object SearchScreen:Screen("searchScreen")
}