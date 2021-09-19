package com.lefarmico.donetime.di.module

import com.lefarmico.donetime.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun provideMainActivity(): MainActivity
}
