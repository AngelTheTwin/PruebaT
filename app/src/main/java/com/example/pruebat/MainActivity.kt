package com.example.pruebat

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pruebat.data.AppDatabase
import com.example.pruebat.data.DataStoreRepository
import com.example.pruebat.notifications.NotificationManagerSingleton
import com.example.pruebat.ui.theme.PruebaTTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

	lateinit var navController: NavHostController

	@OptIn(ExperimentalFoundationApi::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		NotificationManagerSingleton.initialize(this)

		setContent {
			PruebaTTheme {
				val dataStoreRepository = DataStoreRepository(LocalContext.current)
				val mainViewModel: MainViewModel = MainViewModel(dataStoreRepository)


				navController = rememberNavController()
				val context = LocalContext.current.applicationContext
				val database = AppDatabase.getDatabase(context)
				
				SetUpNavGraph(
					navHostController = navController,
					context = context,
					mainViewModel = mainViewModel,
					database = database
				)
			}
		}
	}
}