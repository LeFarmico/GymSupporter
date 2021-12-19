package com.lefarmico.navigation

import android.app.Activity
import android.os.Parcelable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lefarmico.navigation.dialog.Dialog
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.screen.Screen

interface Router {

    fun bind(activity: Activity)

    fun bindNavigationUI(bottomNavigationView: BottomNavigationView)

    fun navigate(
        screen: Screen,
        data: Parcelable? = null,
        sharedElements: Map<Any, String>? = null
    )

    fun show(
        notification: Notification,
        data: Parcelable? = null,
        anchor: Any? = null
    )

    fun showDialog(
        dialog: Dialog
    )

    fun back()
}
