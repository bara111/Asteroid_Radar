package com.udacity.asteroidradar.remote

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.AsteroidService
import com.udacity.asteroidradar.api.NetworkUtils
import com.udacity.asteroidradar.constants.Constants
import com.udacity.asteroidradar.constants.Constants.API_KEY
import com.udacity.asteroidradar.models.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@Suppress("BlockingMethodInNonBlockingContext", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AsteroidRemoteDataSource @Inject constructor(
    var asteroidService: AsteroidService
) : NasaDataSource.Remote {
    var datalist: List<Asteroid>? = null
    var media: PictureOfDay? = null
    suspend fun requestData() {
        val calendar = Calendar.getInstance()
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        val todayDate = dateFormat.format(currentTime)
        calendar.add(Calendar.DAY_OF_YEAR, 7)
        val currentForDayOfWeek = calendar.time
        val seventhDayOfWeek = dateFormat.format(currentForDayOfWeek)

        runBlocking {
            try {
                val result =
                    asteroidService.getData(todayDate, seventhDayOfWeek, API_KEY)
                val imageOfTheDay = asteroidService.getPhoto(API_KEY)
                if (result.isSuccessful) {
                    val imageDataString = imageOfTheDay.body()
                    val imageJsonObject = JSONObject(imageDataString)
                    val dataString = result.body()
                    val asteroidsJsonObject = JSONObject(dataString)
                    withContext(Dispatchers.Default) {
                        datalist = NetworkUtils.parseAsteroidsJsonResult(asteroidsJsonObject)
                        media = NetworkUtils.parseImageJsonResult(imageJsonObject)
                    }
                }
            } catch (e: Exception) {

            }
        }

    }
}







