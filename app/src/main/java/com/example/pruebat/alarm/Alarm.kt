package com.example.pruebat.alarm

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.PendingIntentCompat.Flags
import androidx.core.net.toUri
import com.example.pruebat.APP_URI
import com.example.pruebat.ID_ACTIVITY
import com.example.pruebat.MainActivity
import com.example.pruebat.R
import com.example.pruebat.data.Activity
import com.example.pruebat.notifications.setUpNotification
import java.util.*

// This function sets an alarm at the specified time
fun setAlarm(context: Context, activity: Activity) {
	val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
	val intent = Intent(context, AlarmReceiver::class.java).apply {
		putExtra("ID", activity.id)
		putExtra("NAME", activity.name)
		putExtra("DESCRIPTION", activity.description)
	}

	val pendingIntent = PendingIntent.getBroadcast(context, activity.id, intent, PendingIntent.FLAG_IMMUTABLE)

	// Convert the Date object to milliseconds
	val alarmTimeInMillis = activity.dateTime.time


	// Set the alarm using AlarmManager
	alarmManager.setExact(
		AlarmManager.RTC_WAKEUP,
		alarmTimeInMillis,
//		AlarmManager.INTERVAL_DAY,
		pendingIntent
	)
}

fun cancelAlarm(context: Context, activity: Activity) {
	val alarmIntent = Intent(context, AlarmReceiver::class.java)
	val pendingIntent = PendingIntent.getBroadcast(context, activity.id, alarmIntent, PendingIntent.FLAG_MUTABLE)

	// Cancel the pending intent associated with the alarm
	val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
	alarmManager.cancel(pendingIntent)
}
