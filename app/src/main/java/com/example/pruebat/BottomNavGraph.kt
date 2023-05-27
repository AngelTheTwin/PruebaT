package com.example.pruebat

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pruebat.data.ActivityRepository
import com.example.pruebat.data.AppDatabase
import com.example.pruebat.viewmodels.HomeScreenViewModel
import com.example.pruebat.views.*

@Composable
fun BottonNavGraph(
	navHostController: NavHostController,
	database: AppDatabase
) {
	NavHost(
		navController = navHostController,
		startDestination = BottomBarScreen.Home.route
	) {
		val homeScreenViewModel = HomeScreenViewModel(ActivityRepository(database.activityDao()))
		composable(route = BottomBarScreen.Home.route) {
			HomeScreen(
				homeScreenViewModel,
				navHostController
			)
		}
		composable(route = BottomBarScreen.Estadisticas.route) {
			EstadisticasScreen()
		}
		composable(route = BottomBarScreen.Recompensas.route) {
			RecompensasScreen()
		}
		composable(route = BottomBarScreen.Configuracion.route) {
			ConfiguracionScreen()
		}
		composable(route = "AddActivity") {
			AddActivity(
				homeScreenViewModel,
				navHostController
			)
		}
	}
}