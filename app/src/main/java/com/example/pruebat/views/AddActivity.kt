package com.example.pruebat.views

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.pruebat.data.Activity
import com.example.pruebat.data.ActivityRepository
import com.example.pruebat.ui.theme.PruebaTTheme
import com.example.pruebat.viewmodels.HomeScreenViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.text.Typography

@Composable
fun AddActivity(
	homeScreenViewModel: HomeScreenViewModel,
	navController: NavController
) {
	var activity: Activity by remember {
		mutableStateOf(Activity(
			name = "",
			completed = false,
			isOn = false,
			description = "",
			dateTime = Date()
		))
	}

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp),
		content = {
			Text("Agregar Nueva Actividad", style = MaterialTheme.typography.h6)
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
			TimePicker(initialTime = activity.dateTime, onTimeSelected = {
				activity = activity.copy(dateTime = it)
			}) {

			}
			Row (
				modifier = Modifier,
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = spacedBy(16.dp)
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
						homeScreenViewModel.addActivity(activity)
						navController.navigate(BottomBarScreen.Home.route) {
							popUpTo(navController.graph.findStartDestination().id)
							launchSingleTop = true
						}
					},
					modifier = Modifier.padding(top = 16.dp)
				) {
					Text("Agregar Actividad")
				}
			}
			
		}
	)
}

@Composable
fun LabelledSwitch( // (1)
	modifier: Modifier = Modifier,
	checked: Boolean,
	label: String,
	onCheckedChange: ((Boolean) -> Unit),
	enabled: Boolean = true,
	colors: SwitchColors = SwitchDefaults.colors()
) {

	Box( // (2)
		modifier = modifier
			.fillMaxWidth()
			.height(56.dp)
			.toggleable( // (4)
				value = checked,
				onValueChange = onCheckedChange,
				role = Role.Switch,
				enabled = enabled
			)
//			.padding(horizontal = 16.dp)

	) {
		CompositionLocalProvider(
			LocalContentAlpha provides
					if (enabled) ContentAlpha.high else ContentAlpha.disabled
		) {
			Text( // (3)
				text = label,
				style = MaterialTheme.typography.body1,
				modifier = Modifier
					.align(Alignment.CenterStart)
					.padding(end = 16.dp)
			)
		}

		Switch( // (3)
			checked = checked,
			onCheckedChange = null, // (4)
			enabled = enabled,
			colors = colors,
			modifier = Modifier.align(Alignment.CenterEnd)
		)
	}
}

@Composable
fun TimePicker(
	initialTime: Date,
	onTimeSelected: (Date) -> Unit,
	onDismissRequest: () -> Unit
) {
	var selectedTime by remember { mutableStateOf(initialTime) }
	val timeFormat = SimpleDateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault())
	val timePickerDialog = TimePickerDialog(
		LocalContext.current,
		{_, hourOfDay: Int, minute: Int ->
			val calendar = Calendar.getInstance()
			calendar.time = selectedTime
			calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
			calendar.set(Calendar.MINUTE, minute)
			selectedTime = calendar.time
			onTimeSelected(selectedTime)
		},
		selectedTime.hours,
		selectedTime.minutes,
		true
	)


		Row(
			modifier = Modifier
				.padding(vertical = 16.dp)
				.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Text(
				text = "Seleccione la Hora",
				style = MaterialTheme.typography.body1
			)
			OutlinedButton (
				modifier = Modifier,
//					.padding(8.dp),
				onClick = {
					timePickerDialog.show()
					onTimeSelected(selectedTime)
					onDismissRequest()
				}
			) {
				Text( // (3)
					text = "${selectedTime.hours}:${if (selectedTime.minutes < 10) "0${selectedTime.minutes}" else selectedTime.minutes}",
					style = MaterialTheme.typography.body1,
					modifier = Modifier
						.align(Alignment.CenterVertically)
						.padding(end = 16.dp)
				)
			}
		}

}

@Preview(showBackground = true)
@Composable
fun AddActivityPreview () {
	val repository = ActivityRepository(MockActivityDao())
	PruebaTTheme {
		AddActivity(
			homeScreenViewModel = HomeScreenViewModel(repository),
			navController = NavController(LocalContext.current)
		)
	}
}