package com.lefarmico.core.dialog.setParameter

interface SetSettingDialogCallback : DialogCallback {

    fun addSet(exerciseId: Int, reps: Int, weight: Float)
}
