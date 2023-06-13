package com.example.pruebat

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pruebat.data.ActivityRepository
import com.example.pruebat.data.AppDatabase
import com.example.pruebat.viewmodels.HomeScreenViewModel
import com.example.pruebat.views.*

const val APP_URI = "https://downhealthhabits.com"
const val ID_ACTIVITY: String = "idActivity"

@Composable
fun BottonNavGraph(
	navHostController: NavHostController,
	database: AppDatabase,
	startDestination: String?,
	idActivity: Int? = null
) {
	NavHost(
		navController = navHostController,
		startDestination = startDestination ?: BottomBarScreen.Home.route
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
		composable(route = Screen.AddActivity.route) {
			AddActivity(
				homeScreenViewModel,
				navHostController
			)
		}
		composable(
			route = Screen.UpdateActivity.route,
		) { backStackEntry ->
			val id = idActivity ?: backStackEntry.arguments?.getString(ID_ACTIVITY)?.toInt()
			UpdateActivityView(
				id!!,
				homeScreenViewModel,
				navHostController
			)
		}
	}
}