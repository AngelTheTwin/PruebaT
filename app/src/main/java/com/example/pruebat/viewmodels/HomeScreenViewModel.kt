package com.example.pruebat.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebat.data.Activity
import com.example.pruebat.data.ActivityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeScreenViewModel (private val activityRepository: ActivityRepository): ViewModel() {
	private val _activities = mutableStateListOf<Activity>()
	var activities: SnapshotStateList<Activity> get() = _activities
		set(value) {
			activities = value
		}

	init {
		viewModelScope.launch {
			activityRepository.readAllActivities().collect { activities ->
				_activities.clear()
				_activities.addAll(activities)
			}
		}
	}

	fun addActivity(activity: Activity) {
		viewModelScope.launch {
			withContext(Dispatchers.IO) {
				activityRepository.createActivity(activity)
			}
		}
	}

	fun updateActivity(activity: Activity) {
		viewModelScope.launch {
			withContext(Dispatchers.IO) {
				activityRepository.updateActivity(activity)
			}
		}
	}

	fun deleteActivity(activity: Activity) {
		viewModelScope.launch {
			withContext(Dispatchers.IO) {
				activityRepository.deleteActivity(activity)
			}
		}
	}
}