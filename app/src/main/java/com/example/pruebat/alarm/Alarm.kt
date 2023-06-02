package com.example.pruebat.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.PendingIntentCompat.Flags
import com.example.pruebat.R
import com.example.pruebat.data.Activity
import java.util.*

// This function sets an alarm at the specified time
fun setAlarm(context: Context, activity: Activity) {
	val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
	val intent = Intent(context, AlarmReceiver::class.java)

	val notificationBuilder = NotificationCompat.Builder(context, "Alarms ID")
		.setContentTitle(activity.name)
		.setContentText(activity.description)
		.setSmallIcon(R.drawable.ic_stat_name)
		.setPriority(NotificationCompat.PRIORITY_HIGH)
	val notification = notificationBuilder.build()

	intent.putExtra("notification", notification)

	val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

	// Convert the Date object to milliseconds
	print(activity.dateTime.toLocaleString())
	val alarmTimeInMillis = activity.dateTime.time

	// Set the alarm using AlarmManager
	alarmManager.setExact(
		AlarmManager.RTC_WAKEUP,
		alarmTimeInMillis,
		pendingIntent
	)
}
