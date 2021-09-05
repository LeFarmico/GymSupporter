package com.lefarmico.donetime.customView.setParameters

interface SetSettingDialogCallback : DialogCallback {
    
    fun addSet(exerciseId: Int, reps: Int, weight: Float)
}
