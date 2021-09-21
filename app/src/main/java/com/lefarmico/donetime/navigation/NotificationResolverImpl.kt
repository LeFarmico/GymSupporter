package com.lefarmico.donetime.navigation

import android.app.Activity
import android.os.Parcelable
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.notification.Notification.*
import com.lefarmico.navigation.notification.NotificationResolver
import com.lefarmico.navigation.params.SnackBarParams
import com.lefarmico.navigation.params.ToastBarParams
import javax.inject.Inject

class NotificationResolverImpl @Inject constructor() : NotificationResolver {

    override fun show(
        activity: Activity?,
        notification: Notification,
        data: Parcelable?,
        anchor: Any?
    ) {
        when (notification) {
            TOAST -> showToast(activity, data)
            SNACK_BAR -> showSnackBar(activity, data, anchor)
        }
    }

    private fun showToast(activity: Activity?, data: Parcelable?) {
        if (data !is ToastBarParams) return

        Toast.makeText(activity?.applicationContext, data.message, Toast.LENGTH_LONG).show()
    }

    private fun showSnackBar(activity: Activity?, data: Parcelable?, anchor: Any?) {
        if (data !is SnackBarParams || activity == null) return

        val snackBar = Snackbar.make(
            activity.findViewById(android.R.id.content),
            data.message,
            5000
        )

        snackBar.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                if (event != DISMISS_EVENT_ACTION) {
                    data.listener?.invoke()
                }
            }
        })

        snackBar.show()
    }
}
