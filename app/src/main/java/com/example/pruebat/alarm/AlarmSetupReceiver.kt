package com.example.pruebat.alarm

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.pruebat.R
import com.example.pruebat.data.Activity
import com.example.pruebat.data.AppDatabase
import com.example.pruebat.notifications.NotificationManagerSingleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlarmSetupReceiver: BroadcastReceiver() {
	override fun onReceive(context: Context, intent: Intent) {
		NotificationManagerSingleton.initialize(context)
		val scope = CoroutineScope(Dispatchers.Main)

		scope.launch {
			val activities = retrievePersistedAlarmsFromStorage(context)
			val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

			for (activity in activities) {
				if (activity.isOn) {
					setAlarm(context, activity)
				}
			}
		}
	}

	private suspend fun retrievePersistedAlarmsFromStorage(context: Context): List<Activity> {
		// Retrieve the persisted alarms from Room database
		val database = AppDatabase.getDatabase(context)
		val activityDao = database.activityDao()
		return withContext(Dispatchers.IO) {
			activityDao.readAllActivities().first()
		}
	}
}