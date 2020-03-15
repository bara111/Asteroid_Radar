package com.udacity.asteroidradar.remote

import android.content.Context
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.models.PictureOfDay

interface NasaDataSource {
    suspend fun getData()
    fun getList(): List<Asteroid>?
    fun getImage(): PictureOfDay?
    suspend fun getDailyAsteroid(context: Context): List<Asteroid>?

    interface Remote
}