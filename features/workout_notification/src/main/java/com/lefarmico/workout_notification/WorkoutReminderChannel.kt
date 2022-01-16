package com.lefarmico.workout_notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import androidx.core.app.NotificationManagerCompat

class WorkoutReminderChannel(context: Context) {

    private val channelName = "WorkoutRemindsChannel"
    private val channelDescription = "Workout reminds notification Channel"
    private val importance = NotificationManager.IMPORTANCE_HIGH
    private val notificationManager = NotificationManagerCompat.from(context)

    private val audioAttribute = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
        .setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN)
        .build()

    private val ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

    private val mChannel = NotificationChannel(
        CHANNEL_ID, channelName, importance
    ).apply {
        description = channelDescription
        setSound(ringtone, audioAttribute)
    }

    fun registerChannel() {
        notificationManager.createNotificationChannel(mChannel)
    }

    fun deleteChannel() {
        notificationManager.deleteNotificationChannel(CHANNEL_ID)
    }

    companion object {
        const val CHANNEL_ID = "WorkoutRemindNotificationChannel"
    }
}
