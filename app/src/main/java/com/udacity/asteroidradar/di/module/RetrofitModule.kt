package com.udacity.asteroidradar.di.module

import com.udacity.asteroidradar.api.AsteroidService
import com.udacity.asteroidradar.constants.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
object RetrofitModule {
    @Singleton
    @Provides
    fun getRetrofit(): AsteroidService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build().create(AsteroidService::class.java)
    }

}
