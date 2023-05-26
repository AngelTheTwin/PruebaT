package com.example.pruebat.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
	@Insert(onConflict = OnConflictStrategy.IGNORE)
	fun createActivity(activity: Activity)

	@Query("SELECT * FROM Activity ORDER BY id ASC")
	fun readAllActivities(): Flow<List<Activity>>

	@Query("SELECT * FROM Activity WHERE id = :id")
	fun readActivityById(id: Int): Flow<Activity>

	@Update
	fun updateActivity(activity: Activity)

	@Delete
	fun deleteActivity(activity: Activity)
}