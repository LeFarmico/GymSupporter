package com.lefarmico.donetime.di.module

import androidx.lifecycle.ViewModel
import com.lefarmico.donetime.view.MainActivity
import com.lefarmico.donetime.view.MainViewModel
import com.lefarmico.features.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun provideMainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun addMainViewModel(viewModel: MainViewModel): ViewModel
}
