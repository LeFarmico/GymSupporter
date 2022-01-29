package com.lefarmico.data.di

import android.content.Context
import com.lefarmico.data.dataStore.MuscleCategoryDataStore
import dagger.Module
import dagger.Provides

@Module
class DataStoreModule {

    @Provides
    fun provideMuscleDataStore(context: Context): MuscleCategoryDataStore {
        return MuscleCategoryDataStore(context)
    }
}
