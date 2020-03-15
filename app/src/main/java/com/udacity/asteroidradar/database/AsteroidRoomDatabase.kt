package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.Asteroid

@Database(entities = [Asteroid::class], version = 1, exportSchema = false)
    abstract class AsteroidRoomDatabase : RoomDatabase() {
    abstract fun wordDao(): AsteroidDao
    companion object {
        @Volatile
        private var INSTANCE: AsteroidRoomDatabase? = null
        fun getDatabase(context: Context): AsteroidRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AsteroidRoomDatabase::class.java,
                    "word_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}