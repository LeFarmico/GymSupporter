package com.lefarmico.donetime.views.fragments

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.lefarmico.donetime.databinding.FragmentAddExerciseBinding
import com.lefarmico.donetime.viewModels.AddExerciseViewModel
import com.lefarmico.donetime.views.base.BaseFragment

class AddExerciseFragment : BaseFragment<FragmentAddExerciseBinding, AddExerciseViewModel>(
    FragmentAddExerciseBinding::inflate,
    AddExerciseViewModel::class.java
) {

    private var bundleResult: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBundleResult()
    }
    override fun setUpViews() {
        binding.addButton.setOnClickListener {
            viewModel.addNewExercise(
                getTitleField(),
                getDescriptionField(),
                getImageSource(),
                getSubcategory()
            )
            parentFragmentManager.popBackStack(
                BACK_STACK_KEY,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
    }

    private fun getBundleResult() {
        val bundle = this.arguments
        if (bundle != null) {
            bundleResult = bundle.getInt(KEY_NUMBER)
        }
    }

    private fun getTitleField(): String {
        return binding.exerciseEditText.text.toString()
    }
    private fun getDescriptionField(): String {
        return binding.descriptionEditText.text.toString()
    }
    private fun getImageSource(): String {
        return ""
    }
    private fun getSubcategory(): Int {
        return bundleResult
    }

    companion object {
        const val BACK_STACK_KEY = "add_ex_stack"
        const val KEY_NUMBER = "add_ex_key"
    }
}
