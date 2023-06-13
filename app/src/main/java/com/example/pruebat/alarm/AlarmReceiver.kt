package com.example.pruebat.alarm

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import android.widget.Toast
import androidx.core.content.getSystemService
import com.example.pruebat.notifications.NotificationManagerSingleton
import com.example.pruebat.notifications.setUpNotification

class AlarmReceiver: BroadcastReceiver() {

	override fun onReceive(context: Context, intent: Intent) {
		val id: Int = intent.getIntExtra("ID", 0)
		val name: String = intent.getStringExtra("NAME") ?: throw Error("Error obtaining name from Intent. AlarmReceiver.onReceive")
		val description: String = intent.getStringExtra("DESCRIPTION") ?: throw Error("Error obtaining name from Intent. AlarmReceiver.onReceive")
		Log.d("AlarmReceiver", id.toString())
		val notification: Notification = setUpNotification(context, id, name, description)

		AlarmPlayerSingleton.initiatlize(context)
		AlarmPlayerSingleton.alarmPlayer.start()

		NotificationManagerSingleton.showNotification(id, notification)
	}
}

