package com.example.pruebat.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.*

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "has_tutor_registered")

class DataStoreRepository(context: Context) {

	private object PreferencesKey {
		val hasTutorRegisteredKey = booleanPreferencesKey(name = "has_tutor_registered")
	}

	private val dataStore = context.dataStore
	private val context = context

	suspend fun saveHasTutorRegistered(completed: Boolean) {
		val database = AppDatabase.getDatabase(context)
		val activitiesDao = database.activityDao()
		val activities = listOf<Activity>(
			Activity(name = "Lavarse los dientes.", completed = false, description = "Realizar el lavado correcto de los dientes", dateTime = Date(), isEditable = false, isOn = true),
			Activity(name = "Comer.", completed = false, description = "Comer", dateTime = Date(), isEditable = false, isOn = true),
			Activity(name = "Peinarse.", completed = false, description = "Peinarse", dateTime = Date(), isEditable = false, isOn = true),
		)

		dataStore.edit { preferences ->
			preferences[PreferencesKey.hasTutorRegisteredKey] = completed
		}

		for (activity in activities) {
			Dispatchers.IO.dispatch(Dispatchers.IO) {
				activitiesDao.createActivity(activity)
			}
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