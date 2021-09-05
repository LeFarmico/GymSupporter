package com.lefarmico.donetime.customView.setParameters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lefarmico.donetime.databinding.DialogAddSetBinding

class SetParametersDialog(
    private val exerciseId: Int,
    private val callback: SetSettingDialogCallback
) : BottomSheetDialogFragment() {

    private var _binding: DialogAddSetBinding? = null
    private val binding get() = _binding!!

    val weightValues = arrayKilos()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddSetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.weightPicker.apply {
            minValue = 1
            maxValue = 150
            displayedValues = weightValues
        }
        binding.repsPicker.apply {
            minValue = 1
            maxValue = 100
        }
        binding.addButton.setOnClickListener {
            val repsValue = binding.repsPicker.value
            val weightValue = getWeightValue(binding.weightPicker.value)
            callback.addSet(exerciseId, repsValue, weightValue)
            dismiss()
        }
    }
    
    private fun arrayKilos(): Array<String> {
        val weightsKilos = mutableListOf<String>()
        for (i in 1..150) {
            weightsKilos.add((i * 2.5).toString())
        }
        return weightsKilos.toTypedArray()
    }
    
    private fun getWeightValue(position: Int): Float {
        return weightValues[position - 1].toFloat()
    }
}
