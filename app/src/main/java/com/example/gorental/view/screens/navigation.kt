package com.example.gorental.view.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gorental.utils.AppConstants
import com.example.gorental.utils.AppConstants.WEB_URL
import com.example.gorental.viewmodel.MainViewModel

@Composable
fun AppNavigation(viewModel: MainViewModel){
    val navController = rememberNavController()

    NavHost(navController = navController,
       startDestination = AppConstants.HOME
    ){
       composable(AppConstants.HOME) { HomeScreen(navController,viewModel) }
       composable("${AppConstants.WEB_VIEW}/{$WEB_URL}") { backStackEntry ->
           val url = backStackEntry.arguments?.getString(WEB_URL)?:""
           WebViewScreen(url = url) }
    }

}