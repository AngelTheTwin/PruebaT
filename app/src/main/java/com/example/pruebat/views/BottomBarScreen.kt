package com.example.pruebat.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen (
	val route: String,
	val title: String,
	val icon: ImageVector
) {
	object Home: BottomBarScreen(
		route = "home",
		title = "Actividades",
		icon = Icons.Default.Home
	)

	object  Estadisticas: BottomBarScreen(
		route = "estadisticas",
		title = "Estadisticas",
		icon = Icons.Default.Info
	)

	object  Recompensas: BottomBarScreen(
		route = "recompensas",
		title = "Recompensas",
		icon = Icons.Default.Star
	)

	object  Configuracion: BottomBarScreen(
		route = "configuracion",
		title = "Configuración",
		icon = Icons.Default.Settings
	)
}