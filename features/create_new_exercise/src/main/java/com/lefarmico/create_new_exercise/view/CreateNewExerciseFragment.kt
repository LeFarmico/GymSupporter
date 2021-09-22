package com.lefarmico.create_new_exercise.view

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.FragmentManager
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.create_new_exercise.BuildConfig
import com.lefarmico.create_new_exercise.databinding.FragmentCreateNewExerciseBinding
import com.lefarmico.create_new_exercise.viewModel.AddExerciseViewModel
import com.lefarmico.domain.utils.DataState
import com.lefarmico.navigation.params.WorkoutScreenParams
import java.lang.IllegalArgumentException

class CreateNewExerciseFragment : BaseFragment<FragmentCreateNewExerciseBinding, AddExerciseViewModel>(
    FragmentCreateNewExerciseBinding::inflate,
    AddExerciseViewModel::class.java
) {

    private var bundleResult: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBundleResult()
    }

    override fun setUpViews() {
        binding.addButton.setOnClickListener {
            viewModel.onTriggerEvent(
                com.lefarmico.create_new_exercise.intent.AddExerciseIntent.AddExerciseResult(
                    getTitleField(),
                    getDescriptionField(),
                    getImageSource(),
                    getSubcategory()
                )
            )
        }
    }

    override fun observeData() {
        viewModel.addExerciseLiveData.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                DataState.Empty -> {
                    binding.state.showEmptyState()
                }
                is DataState.Error -> {
                    binding.state.showErrorState()
                }
                DataState.Loading -> {
                    binding.state.showLoadingState()
                }
                is DataState.Success -> {
                    binding.state.showSuccessState()

                    viewModel.onTriggerEvent(com.lefarmico.create_new_exercise.intent.AddExerciseIntent.DefaultState)
                    parentFragmentManager.popBackStack(
                        BACK_STACK_KEY,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                }
            }
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

        private const val KEY_PARAMS = "new_exercise_params"

        fun createBundle(data: Parcelable?): Bundle {
            return Bundle().apply {
                when (data) {
                    is WorkoutScreenParams.NewExercise -> putParcelable(KEY_PARAMS, data)
                    else -> {
                        if (BuildConfig.DEBUG) {
                            throw (
                                IllegalArgumentException(
                                    "data should be NewExerciseParams.Exercise type."
                                )
                                )
                        }
                    }
                }
            }
        }
    }
}
