package com.example.pruebat.data

import androidx.room.TypeConverter
import java.util.Date
import java.text.SimpleDateFormat

class ActivityTypeConverter {
	@TypeConverter
	fun dateToString(date: Date): String {
		val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		return format.format(date)
	}

	@TypeConverter
	fun stringToDate(dateString: String): Date {
		val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		val date: Date = format.parse(dateString) as Date
		return date
	}
}