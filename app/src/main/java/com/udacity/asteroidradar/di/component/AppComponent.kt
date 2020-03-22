package com.udacity.asteroidradar.di.component

import android.content.Context
import com.udacity.asteroidradar.AppSubComponents
import com.udacity.asteroidradar.di.module.DetailsViewModelModule
import com.udacity.asteroidradar.di.module.MainViewModelModule
import com.udacity.asteroidradar.di.module.RetrofitModule
import com.udacity.asteroidradar.di.module.ViewModelFactoryModule
import com.udacity.asteroidradar.ui.detail.DetailsComponent
import com.udacity.asteroidradar.ui.main.MainComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class, AppSubComponents::class, ViewModelFactoryModule::class, DetailsViewModelModule::class, MainViewModelModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun detailsComponent(): DetailsComponent.Factory
    fun mainComponent(): MainComponent.Factory
}