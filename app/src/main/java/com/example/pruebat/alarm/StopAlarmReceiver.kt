package com.example.pruebat.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.Ringtone
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import com.example.pruebat.R

class StopAlarmReceiver: BroadcastReceiver() {
	override fun onReceive(context: Context, intent: Intent) {
		Log.d("StopAlarmReceiver", "SI")
		AlarmPlayerSingleton.alarmPlayer.stop()

		val id = intent.getIntExtra("ID", 0)
		// Cancel the notification
		val notificationManager = NotificationManagerCompat.from(context)
		notificationManager.cancel(id)
	}

}