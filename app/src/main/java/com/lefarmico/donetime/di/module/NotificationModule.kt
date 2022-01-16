package com.lefarmico.donetime.di.module

import android.content.Context
import com.lefarmico.domain.repository.manager.RemindTimeManager
import com.lefarmico.donetime.R
import com.lefarmico.workout_notification.WorkoutRemindNotificationHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NotificationModule {

    @Provides
    @Singleton
    fun provideWorkoutRemindNotificationHelper(context: Context, remindTimeManager: RemindTimeManager): WorkoutRemindNotificationHelper {
        return WorkoutRemindNotificationHelper.Builder()
            .setNavigationGraph(R.navigation.nav_graph)
            .setNavigationDestination(R.id.navigation_home)
            .build(context, remindTimeManager)
    }
}
