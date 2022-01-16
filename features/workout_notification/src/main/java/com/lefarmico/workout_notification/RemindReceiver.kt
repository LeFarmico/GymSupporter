package com.lefarmico.workout_notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.lefarmico.domain.entity.WorkoutRecordsDto
import dagger.android.AndroidInjection
import javax.inject.Inject

class RemindReceiver : BroadcastReceiver() {

    @Inject lateinit var workoutRemindNotificationHelper: WorkoutRemindNotificationHelper

    override fun onReceive(context: Context?, intent: Intent?) {

        AndroidInjection.inject(this, context)
        val bundle = intent?.getBundleExtra(WorkoutRemindNotificationHelper.BUNDLE_KEY)
        val workout: WorkoutRecordsDto.Workout =
            bundle?.get(WorkoutRemindNotificationHelper.DATA_KEY) as WorkoutRecordsDto.Workout

        workoutRemindNotificationHelper.createWorkoutNotification(workout)
    }
}
