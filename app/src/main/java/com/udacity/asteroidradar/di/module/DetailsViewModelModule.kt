package com.udacity.asteroidradar.di.module

import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.di.scope.ViewModelKey
import com.udacity.asteroidradar.ui.detail.DetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DetailsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindMainViewModel(detailsViewModel: DetailsViewModel): ViewModel
}