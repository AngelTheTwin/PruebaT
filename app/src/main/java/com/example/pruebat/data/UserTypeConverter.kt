package com.example.pruebat.data

import androidx.room.TypeConverter

class UserTypeConverter {
	@TypeConverter
	fun toUserType(storedString: String): UserType {
		return UserType.valueOf(storedString)
	}

	@TypeConverter
	fun fromUserType(userType: UserType): String {
		return userType.name
	}
}