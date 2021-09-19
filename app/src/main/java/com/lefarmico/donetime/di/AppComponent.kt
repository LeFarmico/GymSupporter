package com.lefarmico.donetime.di

import android.app.Application
import com.lefarmico.data.di.DaoModule
import com.lefarmico.data.di.DomainModule
import com.lefarmico.data.di.RepositoryModule
import com.lefarmico.donetime.App
import com.lefarmico.donetime.di.module.ActivityBindingModule
import com.lefarmico.features.di.FragmentBindingModule
import com.lefarmico.features.di.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        FragmentBindingModule::class,
        RepositoryModule::class,
        DaoModule::class,
        DomainModule::class,
        ViewModelModule::class
    ]
)
@Singleton
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun domainModule(domainModule: DomainModule): Builder

        fun build(): AppComponent
    }
}
