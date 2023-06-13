package com.example.pruebat

sealed class Screen(val route: String) {
	object Login: Screen("login")
	object Register: Screen("register")
	object Home: Screen("home")
	object AddActivity: Screen("addActivity")
	object UpdateActivity: Screen("updateActivity/{$ID_ACTIVITY}") {
		fun passArgument(idActivity: Int) = "updateActivity/$idActivity"
	}
}
