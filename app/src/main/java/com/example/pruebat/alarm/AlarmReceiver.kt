package com.example.pruebat.alarm

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.pruebat.notifications.NotificationManagerSingleton

class AlarmReceiver: BroadcastReceiver() {
	override fun onReceive(context: Context, intent: Intent) {
		val notification: Notification = intent.getParcelableExtra("notification") ?: throw Error("Error obtaining Notification from Intent. OnReceive")
		NotificationManagerSingleton.showNotification(0, notification)
	}
}