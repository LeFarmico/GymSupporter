package com.lefarmico.donetime

import android.app.Application
import com.lefarmico.donetime.di.AppComponent
import com.lefarmico.donetime.di.DaggerAppComponent
import com.lefarmico.donetime.di.DataBaseModule
import com.lefarmico.donetime.di.DomainModule

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .dataBaseModule(DataBaseModule(this))
            .domainModule(DomainModule())
            .build()
    }
}
