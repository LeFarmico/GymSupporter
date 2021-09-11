package com.lefarmico.presentation.di.modules

import com.lefarmico.presentation.di.scopes.ActivityScope
import com.lefarmico.presentation.views.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun provideMainActivity(): MainActivity
}
