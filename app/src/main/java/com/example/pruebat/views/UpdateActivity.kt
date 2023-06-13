package com.example.pruebat.views

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.pruebat.alarm.cancelAlarm
import com.example.pruebat.alarm.setAlarm
import com.example.pruebat.data.Activity
import com.example.pruebat.data.AppDatabase
import com.example.pruebat.data.User
import com.example.pruebat.data.UserType
import com.example.pruebat.utils.formatDateTime
import com.example.pruebat.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.flow.first
import java.util.*
import kotlin.math.log

@Composable
fun UpdateActivityView (
	idActivity: Int,
	homeScreenViewModel: HomeScreenViewModel,
	navController: NavController,
) {
	val context = LocalContext.current
	var activity: Activity by remember {
		mutableStateOf(
			Activity(
				id = 0,
				name = "",
				description = "",
				isEditable = true,
				isOn = true,
				completed = true,
				dateTime = Date()
			)
		)
	}

	LaunchedEffect(idActivity) {
		val database = AppDatabase.getDatabase(context)
		val activityDao = database.activityDao()
		activity = activityDao.readActivityById(idActivity).first()
		Log.d("UpdateActivityView", activity.dateTime.formatDateTime())
	}
	
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp),
		content = {
			Text("Editar Actividad", style = MaterialTheme.typography.h6)
			OutlinedTextField(
				value = activity.name,
				onValueChange = {
					activity = activity.copy(name = it)
				},
				label = { Text("Nombre") },
				modifier = Modifier.padding(top = 16.dp)
			)
			OutlinedTextField(
				value = activity.description,
				onValueChange = {
					activity = activity.copy(description = it)
				},
				label = { Text("Descripción") },
				modifier = Modifier.padding(top = 16.dp)
			)
			LabelledSwitch(
				checked = activity.isOn,
				label = "Está activa?",
				onCheckedChange = {
					activity = activity.copy(isOn = it)
				}
			)
			TimePicker(
				initialTime = activity.dateTime,
				onTimeSelected = {
				activity = activity.copy(dateTime = it)
				},
				onDismissRequest = {}
			)
			Row (
				modifier = Modifier,
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(16.dp)
			) {
				OutlinedButton(
					onClick = {
						navController.navigate(BottomBarScreen.Home.route) {
							popUpTo(navController.graph.findStartDestination().id)
							launchSingleTop = true
						}
					},
					modifier = Modifier.padding(top = 16.dp)
				) {
					Text(
						text = "Cancelar",
						color = Color.Red
					)
				}
				Button(
					onClick = {
						homeScreenViewModel.updateActivity(activity)
						if (activity.isOn) {
							setAlarm(context, activity)
						} else {
							cancelAlarm(context, activity)
						}
						navController.navigate(BottomBarScreen.Home.route) {
							popUpTo(navController.graph.findStartDestination().id)
							launchSingleTop = true
						}
					},
					modifier = Modifier.padding(top = 16.dp)
				) {
					Text("Actualizar Actividad")
				}
			}

		}
	)
}