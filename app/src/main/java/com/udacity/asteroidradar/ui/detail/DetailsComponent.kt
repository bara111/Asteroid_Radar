package com.udacity.asteroidradar.ui.detail

import dagger.Subcomponent

@Subcomponent
interface DetailsComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): DetailsComponent
    }
    fun inject(activity: DetailsActivity)
}