package com.example.pruebat.views

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pruebat.Screen
import com.example.pruebat.data.Activity
import com.example.pruebat.data.ActivityDao
import com.example.pruebat.data.ActivityRepository
import com.example.pruebat.ui.theme.PruebaTTheme
import com.example.pruebat.utils.formatDateTime
import com.example.pruebat.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Composable
fun HomeScreen(
	homeScreenViewModel: HomeScreenViewModel,
	navController: NavController
) {
	val context = LocalContext.current
	LazyColumn(
		modifier = Modifier
			.fillMaxHeight()
			.background(MaterialTheme.colors.background)
			.padding(horizontal = 8.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		item {
			Text(text = "Actividades", style = MaterialTheme.typography.h4)
		}
		items(homeScreenViewModel.activities) { activity ->
			ActivityItem(
				activity = activity,
				onTap = {
					Log.d("nav", Screen.UpdateActivity.passArgument(activity.id))
						navController.navigate(Screen.UpdateActivity.passArgument(activity.id))
				},
				onToggle = {
					homeScreenViewModel.deactivateActivity(
						activity.copy(isOn = activity.isOn.not()),
						context
					)
				},
				onDelete = {
					homeScreenViewModel.deleteActivity(activity)
				}
			)
		}
		item {
			Button(onClick = {
				navController.navigate(Screen.AddActivity.route)
			}) {
				Text(text = "Add New Activity")
			}
		}

	}
}

@Composable
fun ActivityItem(activity: Activity,onTap: () -> Unit, onToggle: () -> Unit, onDelete: () -> Unit) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 8.dp),
		horizontalArrangement = Arrangement.SpaceBetween,
	) {
		Column(
			modifier = Modifier.weight(1f)
				.clickable {
					onTap()
				}
		) {
			Text(text = activity.name, style = MaterialTheme.typography.body1)
			Text(text = activity.dateTime.formatDateTime(), style = MaterialTheme.typography.caption)
		}
		if (activity.isEditable) {
			Switch(
				checked = activity.isOn,
				onCheckedChange = { onToggle() },
				enabled = true,
				colors = SwitchDefaults.colors(),
				modifier = Modifier
			)
			Icon(
				imageVector = Icons.Default.Delete,
				tint = Color.Red,
				contentDescription = "Delete button",
				modifier = Modifier
					.padding(vertical = 12.dp)
					.fillMaxHeight()
					.clickable {
					onDelete()
				},

			)
		}


	}
}

@Preview(
	showBackground = true,
)
@Composable
fun HomeScreenPreview() {
	val repository = ActivityRepository(MockActivityDao())
	PruebaTTheme {
		HomeScreen(
			homeScreenViewModel = HomeScreenViewModel(repository),
			navController = NavController(context = LocalContext.current)
		)
	}
}

class MockActivityDao: ActivityDao {
	override fun createActivity(activity: Activity): Long {
		TODO("Not yet implemented")
	}

	override fun readAllActivities(): Flow<List<Activity>> {
		TODO("Not yet implemented")
	}

	override fun readActivityById(id: Int): Flow<Activity> {
		TODO("Not yet implemented")
	}

	override fun updateActivity(activity: Activity) {
		TODO("Not yet implemented")
	}

	override fun deleteActivity(activity: Activity) {
		TODO("Not yet implemented")
	}
}