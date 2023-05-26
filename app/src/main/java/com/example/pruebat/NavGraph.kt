package com.example.pruebat

import android.app.Application
import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pruebat.data.AppDatabase
import com.example.pruebat.views.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SetUpNavGraph(
	navHostController: NavHostController,
	context: Context,
	mainViewModel: MainViewModel,
	database: AppDatabase
) {
	val startDestination by mainViewModel.startDestination


	NavHost(navController = navHostController, startDestination = startDestination) {
		composable(
			route = Screen.Login.route
		) {
			LoginScreen(
				viewModel = UsersViewModel(context as Application),
				navController = navHostController
			)
		}

		composable(
			route = Screen.Register.route
		) {
			RegisterScreen(
				mainViewModel = mainViewModel,
				viewModel = UsersViewModel(context as Application),
				navController = navHostController
			)
		}

		composable(
			route = Screen.Home.route
		) {
			MainScreen(
				database = database
			)
		}
	}
}