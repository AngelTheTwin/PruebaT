package com.example.pruebat.data

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.TypeConverters


@Database(
	entities = [User::class, Activity::class],
	version = 2,
	exportSchema = false
)
@TypeConverters( ActivityTypeConverter::class, UserTypeConverter::class,)
abstract class AppDatabase: RoomDatabase() {
	abstract fun userDao(): UserDao
	abstract fun activityDao(): ActivityDao

	companion object {
		@Volatile
		private var INSTANCE: AppDatabase? = null

		fun getDatabase(context: Context): AppDatabase {
			return INSTANCE ?: synchronized(this) {
				Room.databaseBuilder(
					context,
					AppDatabase::class.java,
					"app_database"
				)
			}
				.fallbackToDestructiveMigration()
				.build()
				.also { INSTANCE = it }
		}
	}


}