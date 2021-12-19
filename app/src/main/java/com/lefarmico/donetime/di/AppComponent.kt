package com.lefarmico.donetime.di

import android.app.Application
import android.content.Context
import com.lefarmico.data.di.*
import com.lefarmico.donetime.App
import com.lefarmico.donetime.di.module.ActivityBindingModule
import com.lefarmico.donetime.di.module.NavigationModule
import com.lefarmico.features.di.module.FragmentBindingModule
import com.lefarmico.features.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityBindingModule::class,
        FragmentBindingModule::class,
        RepositoryModule::class,
        DaoModule::class,
        ViewModelModule::class,
        NavigationModule::class,
        PreferenceModule::class,
        ManagerModule::class,
        LocalCacheModule::class
    ]
)
@Singleton
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}
