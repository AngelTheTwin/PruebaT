package com.example.pruebat.Views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pruebat.BottonNavGraph

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
	val bottomBarNavController = rememberNavController()

	Scaffold(
		bottomBar = { BottomBar(navController = bottomBarNavController)}
	) {
		BottonNavGraph(navHostController = bottomBarNavController)
	}
}

@Composable
fun BottomBar(navController: NavHostController) {
	val screens = listOf(
		BottomBarScreen.Home,
		BottomBarScreen.Estadisticas,
		BottomBarScreen.Recompensas,
		BottomBarScreen.Configuracion
	)

	val navBackStackEntry by navController.currentBackStackEntryAsState()
	val currentDestination = navBackStackEntry?.destination

	BottomNavigation {
		screens.forEach{ screen ->
			AddItem(
				screen = screen,
				currentDestination = currentDestination,
				navController = navController
			)
		}
	}
}

@Composable
fun RowScope.AddItem(
	screen: BottomBarScreen,
	currentDestination: NavDestination?,
	navController: NavHostController
) {
	BottomNavigationItem(
		label= {
			Text(
				text = screen.title,
				fontSize = 11.sp
			)
		},
		icon = {
			Icon(
				imageVector = screen.icon,
				contentDescription = "${screen.title} icon"
			)
		},
		selected = currentDestination?.hierarchy?.any {
			it.route == screen.route
		} == true,
		onClick = {
			navController.navigate(screen.route) {
				popUpTo(navController.graph.findStartDestination().id)
				launchSingleTop = true
			}
		}
	)
}