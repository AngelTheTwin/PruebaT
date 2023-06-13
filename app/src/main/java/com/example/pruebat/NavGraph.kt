package com.example.pruebat

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
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
//	val startDestination by mainViewModel.startDestination

	var bottomBarNavController = rememberNavController()


	NavHost(navController = navHostController, startDestination = Screen.Login.route) {
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

		composable(
			route = Screen.UpdateActivity.route,
			arguments = listOf(navArgument(ID_ACTIVITY) { type = NavType.IntType }),
			deepLinks = listOf(navDeepLink {
				uriPattern = "$APP_URI/$ID_ACTIVITY={$ID_ACTIVITY}"
			})
		) { backStackEntry ->
			val arguments = backStackEntry.arguments
			arguments?.getInt(ID_ACTIVITY)?.let { idActivity ->
				Log.d("Activity ID", idActivity.toString())
				MainScreen(
					database = database,
					startDestination = Screen.UpdateActivity.route,
					idActivity = idActivity
				)
			}
//
		}
	}
}