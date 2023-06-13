package com.example.pruebat.data

import kotlinx.coroutines.flow.Flow

class ActivityRepository(private val activityDao: ActivityDao) {
	fun createActivity(activity: Activity): Int = activityDao.createActivity(activity).toInt()

	fun readAllActivities(): Flow<List<Activity>> = activityDao.readAllActivities()

	fun readActivityByd(id: Int): Flow<Activity> = activityDao.readActivityById(id)

	fun updateActivity(activity: Activity) = activityDao.updateActivity(activity)

	fun deleteActivity(activity: Activity) = activityDao.deleteActivity(activity)
}