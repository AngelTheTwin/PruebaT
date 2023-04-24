package com.example.pruebat.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "User")
data class User (
	@PrimaryKey(autoGenerate = true)
	val id: Int,
	val nombre: String,
	val username: String,
	@TypeConverters(UserTypeConverter::class)
	val type: UserType,
	val password: String,
	var imageUrl: String
)

enum class UserType {
	TUTOR,
	USUARIO
}