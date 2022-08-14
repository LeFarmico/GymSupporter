package com.lefarmico.donetime.navigation

import android.app.Activity
import android.os.Parcelable
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lefarmico.donetime.R
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.dialog.Dialog
import com.lefarmico.navigation.dialog.DialogResolver
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.notification.NotificationResolver
import com.lefarmico.navigation.screen.Screen
import com.lefarmico.navigation.screen.ScreenResolver
import javax.inject.Inject

class RouterImpl @Inject constructor(
    private val screenResolver: ScreenResolver,
    private val notificationResolver: NotificationResolver,
    private val dialogResolver: DialogResolver
) : Router {

    private var navController: NavController? = null
    private var activity: Activity? = null
    private var fragmentManager: FragmentManager? = null

    override fun bind(activity: Activity) {
        this.activity = activity
        this.fragmentManager = (activity as FragmentActivity).supportFragmentManager
    }

    override fun bindNavController(navController: NavController) {
        this.navController = navController
    }

    override fun bindNavigationUI(bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.setupWithNavController(
            navController
                ?: throw NullPointerException("NavController is not implemented")
        )
    }

    override fun back() {
        navController?.navigateUp()
    }

    override fun show(
        notification: Notification,
        data: Parcelable?,
        anchor: Any?
    ) {
        notificationResolver.show(activity, notification, data, anchor)
    }

    override fun showDialog(
        dialog: Dialog
    ) {
        dialogResolver.show(fragmentManager!!, dialog)
    }

    override fun navigate(
        screen: Screen,
        data: Parcelable?,
        sharedElements: Map<Any, String>?
    ) {
        screenResolver.navigate(navController, screen, data, sharedElements)
    }
}
