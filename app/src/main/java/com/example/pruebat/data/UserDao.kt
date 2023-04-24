package com.example.pruebat.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
	@Insert(onConflict = OnConflictStrategy.IGNORE)
	fun createUser(user: User)

	@Query("SELECT * FROM User ORDER BY id ASC")
	fun readAllUsers(): Flow<List<User>>

	@Query("SELECT * FROM User WHERE id = :id")
	fun readUserById(id: Int): Flow<User>

	@Update
	fun updateUser(user: User)

	@Delete
	fun deleteUser(user: User)
}