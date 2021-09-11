package com.lefarmico.donetime

import com.lefarmico.data.di.DomainModule
import com.lefarmico.donetime.di.AppComponent
import com.lefarmico.donetime.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .domainModule(DomainModule(this))
            .build()
        return appComponent
    }
}
