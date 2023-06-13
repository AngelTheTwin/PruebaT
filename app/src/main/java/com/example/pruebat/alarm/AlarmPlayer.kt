package com.example.pruebat.alarm

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager

class AlarmPlayer(private val context: Context) {
	private var ringtone: Ringtone? = null

	fun start() {
		val ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
		ringtone = RingtoneManager.getRingtone(context, ringtoneUri)
		ringtone?.play()
	}

	fun stop() {
		ringtone?.stop()
	}
}

object AlarmPlayerSingleton {
	lateinit var alarmPlayer: AlarmPlayer

	fun initiatlize(context: Context) {
		alarmPlayer = AlarmPlayer(context)
	}
}