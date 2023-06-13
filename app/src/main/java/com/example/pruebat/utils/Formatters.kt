package com.example.pruebat.utils

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatDateTime(): String {
	return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(this)
}