package com.example.pruebat.Views

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebat.data.AppDatabase
import com.example.pruebat.data.User
import com.example.pruebat.data.UserRepository
import com.example.pruebat.data.UserType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UsersViewModel(application: Application): ViewModel() {

	private val repository: UserRepository
	private var _usuarios: Flow<List<User>>? = null
	val usuarios: Flow<List<User>>
	get() = _usuarios!!

	init {
		val appDb = AppDatabase.getDatabase(application)
		val userDao = appDb.userDao()
		repository = UserRepository(userDao)
		viewModelScope.launch {
			_usuarios = repository.readAllUsers()
		}
	}

	fun saveUser(user: User) {
		viewModelScope.launch(Dispatchers.IO) {
			repository.createUser(user)
		}
	}

	fun updateUser(user: User) {
		viewModelScope.launch {
			repository.updateUser(user)
		}
	}

	fun deleteUser(user: User) {
		viewModelScope.launch {
			repository.deleteUser(user)
		}
	}
}