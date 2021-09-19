package com.lefarmico.features.di

import dagger.Subcomponent

@Subcomponent(
    modules = [
        ViewModelModule::class
    ]
)
interface FeaturesComponent
