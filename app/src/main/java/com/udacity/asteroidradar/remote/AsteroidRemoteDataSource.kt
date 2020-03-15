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
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*

@Suppress("BlockingMethodInNonBlockingContext", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AsteroidRemoteDataSource(
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
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val service: AsteroidService = retrofit.create(AsteroidService::class.java)
        runBlocking {
            try {
                val result =
                    service.getData(todayDate, seventhDayOfWeek, API_KEY)
                val imageOfTheDay = service.getPhoto(API_KEY)
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







