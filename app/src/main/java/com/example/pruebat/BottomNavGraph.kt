package com.example.pruebat

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pruebat.Views.*

@Composable
fun BottonNavGraph(navHostController: NavHostController) {
	NavHost(
		navController = navHostController,
		startDestination = BottomBarScreen.Home.route
	) {
		composable(route = BottomBarScreen.Home.route) {
			HomeScreen()
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
	}
}