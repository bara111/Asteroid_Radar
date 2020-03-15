package com.udacity.asteroidradar.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.remote.AsteroidRepository
import com.udacity.asteroidradar.models.PictureOfDay
import com.udacity.asteroidradar.workers.AsteroidWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit


class MainViewModel : ViewModel() {
    private var asteroidRepository: AsteroidRepository =
        AsteroidRepository()
    private var _asteroidDataList: MutableLiveData<List<Asteroid>>? = MutableLiveData()
    private var _mediaData: MutableLiveData<PictureOfDay?> = MutableLiveData()
    val asteroidDailyDataList: LiveData<List<Asteroid>>? get() = _asteroidDataList
    val mediaData: LiveData<PictureOfDay?> get() = _mediaData
    private val constraints: Constraints = Constraints.Builder()
        .setRequiresCharging(true)
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    fun getDataFromNetwork() {
        GlobalScope.launch(Dispatchers.IO) {
            asteroidRepository.getData()
            withContext(Dispatchers.Main) {
                _asteroidDataList?.value = asteroidRepository.getList()
                _mediaData.value = asteroidRepository.getImage()
                val refreshWork =
                    PeriodicWorkRequest.Builder(AsteroidWorker::class.java, 10, TimeUnit.SECONDS)
                        .setConstraints(constraints)
                        .build()
                WorkManager.getInstance().enqueue(refreshWork)
            }
        }
    }

    fun getDataFromDatabase(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            asteroidRepository.getDailyAsteroid(context)
            withContext(Dispatchers.Main) {
                _asteroidDataList?.value = asteroidRepository.getDailyAsteroid(context)
            }
        }
    }
}


