package com.example.pruebat.views

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pruebat.data.Activity
import com.example.pruebat.data.ActivityDao
import com.example.pruebat.data.ActivityRepository
import com.example.pruebat.ui.theme.PruebaTTheme
import com.example.pruebat.utils.formatDateTime
import com.example.pruebat.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.flow.Flow
import java.util.Date

var activities = listOf(
	Activity(1, "Activity 1", completed = false, "Description 1", isOn = true, Date()),
	Activity(2, "Activity 2", completed = false, "Description 2", isOn = true, Date()),
	Activity(3, "Activity 3", completed = true, "Description 3", isOn = false, Date())
)

@Composable
fun HomeScreen(
	homeScreenViewModel: HomeScreenViewModel,
	navController: NavController
) {

	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colors.background)
			.padding(horizontal = 8.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(text = "Actividades", style = MaterialTheme.typography.h4)
		ActivityList(homeScreenViewModel.activities) { index, activity ->
			homeScreenViewModel.updateActivity(activity)
		}
		Button(onClick = {
			navController.navigate("AddActivity")
//			homeScreenViewModel.addActivity(
//				Activity(id = 4, name =  "Activity 3", completed = false, description = "Description 2", isOn = false, dateTime = Date())
//			)
		}) {
			Text(text = "Add New Activity")
		}
	}
}

@Composable
fun ActivityList(activities: SnapshotStateList<Activity>, updateActivity: (Int, Activity) -> Unit) {
	// Sort activities by date, elders first
	val sortedActivities = activities.sortedBy { it.dateTime }

	Column(modifier = Modifier.padding(top = 16.dp)) {
		sortedActivities.forEachIndexed { index, activity ->
			ActivityItem(activity = activity) {
				updateActivity(index, activity.copy(isOn = activity.isOn.not()))
			}
		}
	}


}

@Composable
fun ActivityItem(activity: Activity, onToggle: () -> Unit) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 8.dp),
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Column(modifier = Modifier.weight(1f)) {
			Text(text = activity.name, style = MaterialTheme.typography.body1)
			Text(text = activity.dateTime.formatDateTime(), style = MaterialTheme.typography.caption)
		}
		Checkbox(
			checked = activity.isOn,
			onCheckedChange = { onToggle() }
		)
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
	override fun createActivity(activity: Activity) {
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