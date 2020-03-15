package com.udacity.asteroidradar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDao {

    @Query("SELECT * from asteroid_table where closeApproachDate = :date ")
    fun getRecords(date: String): List<Asteroid>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(asteroid: Asteroid)

    @Query("DELETE FROM asteroid_table")
    fun deleteAll()
}