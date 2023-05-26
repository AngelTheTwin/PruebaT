package com.example.pruebat.utils

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatDateTime(): String {
	val today = Date()
	val isSameDay = this.year == today.year && this.month == today.month && this.day == today.day

	return if (isSameDay) {
		val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
		formatter.format(this)
	} else {
		val formatter = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
		formatter.format(this)
	}
}