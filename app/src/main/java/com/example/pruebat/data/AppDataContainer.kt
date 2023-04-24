package com.example.pruebat.data

import android.content.Context

interface  AppContainer {
	val userRepository: UserRepository
}

class AppDataContainer(private  val context: Context): AppContainer {
	override val userRepository: UserRepository by lazy {
		UserRepository(AppDatabase.getDatabase(context).userDao())
	}
}