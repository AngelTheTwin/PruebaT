package com.example.pruebat.notifications

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.example.pruebat.APP_URI
import com.example.pruebat.ID_ACTIVITY
import com.example.pruebat.MainActivity
import com.example.pruebat.R
import com.example.pruebat.alarm.AlarmPlayerSingleton
import com.example.pruebat.alarm.StopAlarmReceiver
import com.example.pruebat.data.Activity

object NotificationManagerSingleton {
	private lateinit var notificationManager: NotificationManager

	fun initialize(context: Context) {
		notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		val channel = NotificationChannel(
			"Alarms ID",
			"Alarms",
			NotificationManager.IMPORTANCE_HIGH
		)
		notificationManager.createNotificationChannel(channel)
	}

	fun showNotification(notificationId: Int, notification: Notification) {
		notificationManager.notify(notificationId, notification)
	}
}

fun setUpNotification(context: Context, id: Int, name: String, description: String): Notification {
	val stringUri = "$APP_URI/$ID_ACTIVITY=${id}"
	val clickIntent = Intent(
		Intent.ACTION_VIEW,
		stringUri.toUri(),
		context,
		MainActivity::class.java
	)
	val clickPendingIntent = TaskStackBuilder.create(context).run {
		addNextIntentWithParentStack(clickIntent)
		getPendingIntent(1, PendingIntent.FLAG_IMMUTABLE)
	}

	val stopAlarmPendingIntent = createStopSoundIntent(context, id)

	val notificationBuilder = NotificationCompat.Builder(context, "Alarms ID")
		.setContentTitle(name)
		.setSmallIcon(R.drawable.notification_logo)
		.setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
		.setContentText(description)
		.setPriority(NotificationCompat.PRIORITY_HIGH)
		.setContentIntent(clickPendingIntent)
		.addAction(R.drawable.ic_action_stop, "Stop", stopAlarmPendingIntent)


	val notification = notificationBuilder.build()
	return notification
}


fun createStopSoundIntent(context: Context, id: Int): PendingIntent {
	val stopActionIntent = Intent(context, StopAlarmReceiver::class.java).apply {
		putExtra("ID", id)
	}
	val stopActionPendingIntent = PendingIntent.getBroadcast(
		context,
		0,
		stopActionIntent,
		PendingIntent.FLAG_IMMUTABLE
	)
	return  stopActionPendingIntent;
}