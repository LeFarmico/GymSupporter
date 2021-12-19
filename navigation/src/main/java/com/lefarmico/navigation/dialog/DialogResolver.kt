package com.lefarmico.navigation.dialog

import androidx.fragment.app.FragmentManager

interface DialogResolver {

    fun show(
        fragmentManager: FragmentManager,
        dialog: Dialog
    )
}
