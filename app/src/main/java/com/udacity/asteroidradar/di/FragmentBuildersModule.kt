package com.udacity.asteroidradar.di

import com.udacity.asteroidradar.ui.detail.DetailFragment
import com.udacity.asteroidradar.ui.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeRepoFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeUserFragment(): DetailFragment
}
