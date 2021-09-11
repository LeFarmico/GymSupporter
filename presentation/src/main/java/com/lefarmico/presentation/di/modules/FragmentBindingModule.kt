package com.lefarmico.presentation.di.modules

import com.lefarmico.presentation.di.scopes.FragmentScope
import com.lefarmico.presentation.views.fragments.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {
    
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideHomeFragment(): HomeFragment
}
