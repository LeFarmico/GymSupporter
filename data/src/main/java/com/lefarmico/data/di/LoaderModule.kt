package com.lefarmico.data.di

import com.lefarmico.data.loader.MuscleCategoryLoaderImpl
import com.lefarmico.domain.loaders.MuscleCategoryLoader
import dagger.Binds
import dagger.Module

@Module
abstract class LoaderModule {

    @Binds
    abstract fun provideMuscleLoaderModule(loader: MuscleCategoryLoaderImpl): MuscleCategoryLoader
}
