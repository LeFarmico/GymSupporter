package com.lefarmico.core.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lefarmico.core.databinding.DialogAddSetBinding
import com.lefarmico.navigation.params.SetParameterParams

class SetParameterPickerDialog(
    private val exerciseId: Int,
    private val callback: (SetParameterParams) -> Unit = {}
) : BottomSheetDialogFragment() {

    private var _binding: DialogAddSetBinding? = null
    private val binding get() = _binding!!

    private val weightValues = arrayKilos()

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
            val reps = binding.repsPicker.value
            val weight = getWeightValue(binding.weightPicker.value)
            val params = SetParameterParams(exerciseId, reps, weight)
            callback(params)
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

    companion object {
        const val TAG = "SetParameterPicker"
    }
}
