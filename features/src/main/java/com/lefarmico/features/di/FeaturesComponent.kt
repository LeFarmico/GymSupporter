package com.lefarmico.features.di

import dagger.Subcomponent

@FragmentScope
@Subcomponent(
    modules = [
        ViewModelModule::class
    ]
)
interface FeaturesComponent
