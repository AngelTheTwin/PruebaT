package com.example.pruebat.Views

import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.pruebat.R

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