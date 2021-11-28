package com.lefarmico.features.di

import dagger.Component

@FeatureScope
@Component(
    modules = [
        ViewModelModule::class
    ]
)
interface FeaturesComponent
