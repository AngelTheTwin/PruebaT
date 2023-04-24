package com.example.pruebat.data

import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
	suspend fun createUser(user: User) = userDao.createUser(user)

	fun readAllUsers(): Flow<List<User>> = userDao.readAllUsers()

	fun readUserById(id: Int): Flow<User> = userDao.readUserById(id)

	suspend fun updateUser(user: User) = userDao.updateUser(user)

	suspend fun deleteUser(user: User) = userDao.deleteUser(user)
}