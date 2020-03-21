package com.udacity.asteroidradar.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.ui.detail.DetailsViewModel
import com.udacity.asteroidradar.ui.main.MainViewModel
import com.udacity.asteroidradar.viewmodel.AsteroidViewModelFactory

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindUserViewModel(userViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: DetailsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AsteroidViewModelFactory): ViewModelProvider.Factory
}
