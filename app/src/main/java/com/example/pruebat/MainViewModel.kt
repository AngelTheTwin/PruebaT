package com.example.pruebat

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebat.data.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel constructor(
	private val repository: DataStoreRepository
) : ViewModel() {

	private  val _hasTutorRegistered: MutableState<Boolean> = mutableStateOf(false)
	val hasTutorRegistered: State<Boolean> = _hasTutorRegistered

	private val _startDestination: MutableState<String> = mutableStateOf(Screen.Register.route)
	val startDestination: State<String> = _startDestination

	init {
		viewModelScope.launch(Dispatchers.Main) {
			repository.readHasTutorRegistered().collect { completed ->
				_hasTutorRegistered.value = completed
				if (completed) {
					_startDestination.value = Screen.Login.route
				} else {
					_startDestination.value = Screen.Register.route
				}
			}
		}
	}

	fun saveHasTutorRegistered(completed: Boolean) {
		viewModelScope.launch(Dispatchers.IO) {
			repository.saveHasTutorRegistered(completed)
		}
	}
}