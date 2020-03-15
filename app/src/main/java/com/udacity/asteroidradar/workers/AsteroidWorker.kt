package com.udacity.asteroidradar.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.api.AsteroidService
import com.udacity.asteroidradar.api.NetworkUtils
import com.udacity.asteroidradar.constants.Constants
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.database.AsteroidRoomDatabase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AsteroidWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
    private var daoDatabase: AsteroidDao = AsteroidRoomDatabase.getDatabase(context).wordDao()
    override suspend fun doWork(): Result = coroutineScope {
        val service: AsteroidService = retrofit.create(AsteroidService::class.java)
        val jobs = (0 until 100).map {
            async {
                val calendar = Calendar.getInstance()
                val currentTime = calendar.time
                val dateFormat =
                    SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
                val todayDate = dateFormat.format(currentTime)
                calendar.add(Calendar.DAY_OF_YEAR, 7)
                val currentForDayOfWeek = calendar.time
                val seventhDayOfWeek = dateFormat.format(currentForDayOfWeek)
                val result = service.getData(todayDate, seventhDayOfWeek, Constants.API_KEY)
                val dataString = result.body()
                val asteroidsJsonObject = JSONObject(dataString)
                val dataList = NetworkUtils.parseAsteroidsJsonResult(asteroidsJsonObject)
                for (data in dataList) {
                    daoDatabase.insert(data)
                }
            }
        }

        jobs.awaitAll()
        Result.success()
    }
}