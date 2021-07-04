package com.lefarmico.donetime

import android.app.Application
import com.lefarmico.donetime.di.AppComponent
import com.lefarmico.donetime.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}
