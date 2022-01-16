package com.lefarmico.workout_notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.repository.manager.RemindTimeManager
import java.time.ZoneId
import javax.inject.Inject

class WorkoutRemindNotificationHelper @Inject private constructor(
    private val navigationGraph: Int,
    private val destination: Int,
    private val context: Context,
    private val remindTimeManager: RemindTimeManager
) {

    class Builder {
        var navigationGraph: Int? = null
        var destination: Int? = null

        fun setNavigationGraph(@NavigationRes navigationResId: Int) = apply {
            navigationGraph = navigationResId
        }

        fun setNavigationDestination(@IdRes destinationRes: Int) = apply {
            destination = destinationRes
        }

        fun build(context: Context, remindTimeManager: RemindTimeManager): WorkoutRemindNotificationHelper {
            return when (navigationGraph == null || destination == null) {
                true -> throw (NullPointerException("All properties must be initialised."))
                false -> WorkoutRemindNotificationHelper(navigationGraph!!, destination!!, context, remindTimeManager)
            }
        }
    }
    fun createWorkoutNotification(workout: WorkoutRecordsDto.Workout) {
        val notificationManager = NotificationManagerCompat.from(context)
        val bundle = Bundle().apply {
            putParcelable(DATA_KEY, workout)
        }
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(navigationGraph)
            .setDestination(destination)
            .setArguments(bundle)
            .createPendingIntent()

        remindTimeManager.getSelectedRemindTime()
            .observeUi()
            .doAfterSuccess { dto ->
                val title = context.getString(R.string.next_workout_notification_title)
                val description = context.getString(R.string.next_workout_notification, dto.hoursBefore)
                val notification = NotificationCompat.Builder(context, WorkoutReminderChannel.CHANNEL_ID).apply {
                    priority = NotificationCompat.PRIORITY_HIGH
                    setSmallIcon(R.drawable.ic_launcher_foreground)
                    setContentTitle(title)
                    setContentText(description)
                    setContentIntent(pendingIntent)
                    setAutoCancel(true)
                }
                notificationManager.notify(workout.id, notification.build())
            }.subscribe()
    }

    fun startWorkoutReminderEvent(workout: WorkoutRecordsDto.Workout) {
        remindTimeManager.getSelectedRemindTime()
            .observeUi()
            .doAfterSuccess { dto ->
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(context, RemindReceiver()::class.java)
                val bundle = Bundle().apply {
                    putParcelable(DATA_KEY, workout)
                }
                intent.putExtra(BUNDLE_KEY, bundle)
                val pendingIntent = PendingIntent.getBroadcast(context, workout.id, intent, PendingIntent.FLAG_ONE_SHOT)
                val remindTimeInMillis = workout.date
                    .atTime(workout.time)
                    .minusHours(dto.hoursBefore.toLong())
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()

                alarmManager.set(AlarmManager.RTC_WAKEUP, remindTimeInMillis, pendingIntent)
            }.subscribe()
    }

    companion object {

        const val BUNDLE_KEY = "WorkoutNotification"
        const val DATA_KEY = "WorkoutKey"
    }
}
