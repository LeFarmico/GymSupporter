package com.lefarmico.donetime.di

import android.app.Application
import com.lefarmico.presentation.di.PresentationComponent
import com.lefarmico.presentation.di.modules.ActivityBindingModule
import com.lefarmico.presentation.di.modules.DataBaseModule
import com.lefarmico.presentation.di.modules.DomainModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class
    ]
)
@Singleton
interface AppComponent {

    fun presentationComponent(
        dataBaseModule: DataBaseModule,
        domainModule: DomainModule
    ): PresentationComponent

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
