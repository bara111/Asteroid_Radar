package com.udacity.asteroidradar.di.module

import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.di.scope.ViewModelKey
import com.udacity.asteroidradar.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel
}