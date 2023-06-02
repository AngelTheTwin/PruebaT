package com.example.pruebat.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

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