package com.udacity.asteroidradar.di.module

import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.ViewModelFactory
import com.udacity.asteroidradar.remote.AsteroidRemoteDataSource
import com.udacity.asteroidradar.remote.NasaDataSource

import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    abstract fun bindWeatherRemoteDataSource(asteroidRemoteDataSource: AsteroidRemoteDataSource): NasaDataSource.Remote
}