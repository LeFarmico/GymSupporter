package com.lefarmico.donetime

import android.os.StrictMode
import com.lefarmico.donetime.di.AppComponent
import com.lefarmico.donetime.di.DaggerAppComponent
import com.lefarmico.workout_notification.WorkoutReminderChannel
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyDeath()
                .build()

            StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyDeath()
                .build()
        }
        WorkoutReminderChannel(this).registerChannel()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .context(this)
            .build()
        return appComponent
    }
}
