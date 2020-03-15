package com.udacity.asteroidradar.remote

import android.content.Context
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.constants.Constants.todayDate
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.database.AsteroidRoomDatabase
import com.udacity.asteroidradar.models.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidRepository() : NasaDataSource {
    private var asteroidRemoteDataSource: AsteroidRemoteDataSource = AsteroidRemoteDataSource()
    var data: List<Asteroid>? = null
    override fun getList(): List<Asteroid>? {
        return asteroidRemoteDataSource.datalist
    }

    override fun getImage(): PictureOfDay? {
        return asteroidRemoteDataSource.media
    }

    override suspend fun getDailyAsteroid(context: Context): List<Asteroid>? {
        val daoDatabase: AsteroidDao = AsteroidRoomDatabase.getDatabase(context).wordDao()
        withContext(Dispatchers.IO) {
            data = daoDatabase.getRecords(todayDate)
        }
        return data
    }

    override suspend fun getData() {
        asteroidRemoteDataSource.requestData()
    }
}