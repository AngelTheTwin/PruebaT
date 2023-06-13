package com.example.pruebat.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "Activity")
data class Activity(
	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,
	val name: String,
	val completed: Boolean,
	val description: String,
	var isOn: Boolean,
	var isEditable: Boolean,
	@TypeConverters(ActivityTypeConverter::class)
	val dateTime: Date = Date(),
)