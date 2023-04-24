package com.example.pruebat.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "has_tutor_registered")

class DataStoreRepository(context: Context) {

	private object PreferencesKey {
		val hasTutorRegisteredKey = booleanPreferencesKey(name = "has_tutor_registered")
	}

	private val dataStore = context.dataStore

	suspend fun saveHasTutorRegistered(completed: Boolean) {
		dataStore.edit { preferences ->
			preferences[PreferencesKey.hasTutorRegisteredKey] = completed
		}
	}

	fun readHasTutorRegistered(): Flow<Boolean> {
		return dataStore.data
			.catch { exception ->
				if (exception is IOException) {
					emit(emptyPreferences())
				} else {
					throw exception
				}
			}
			.map { preferences ->
				val onBoardingState = preferences[PreferencesKey.hasTutorRegisteredKey] ?: false
				onBoardingState
			}
	}
}