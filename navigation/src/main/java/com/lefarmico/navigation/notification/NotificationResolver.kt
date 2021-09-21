package com.lefarmico.navigation.notification

import android.app.Activity
import android.os.Parcelable

interface NotificationResolver {

    fun show(
        activity: Activity?,
        notification: Notification,
        data: Parcelable? = null,
        anchor: Any? = null
    )
}
