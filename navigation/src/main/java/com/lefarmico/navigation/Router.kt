package com.lefarmico.navigation

import android.app.Activity
import android.os.Parcelable
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.screen.Screen

interface Router {

    fun bind(activity: Activity)

    fun navigate(
        screen: Screen,
        data: Parcelable? = null,
        sharedElements: Map<Any, String>? = null
    )

    fun show(
        notification: Notification,
        data: Parcelable? = null,
        anchor: Any? = null // should be a view
    )

    fun back()
}
