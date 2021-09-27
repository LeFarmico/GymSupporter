package com.lefarmico.create_new_exercise.view

import android.os.Bundle
import android.os.Parcelable
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.create_new_exercise.BuildConfig
import com.lefarmico.create_new_exercise.databinding.FragmentCreateNewExerciseBinding
import com.lefarmico.create_new_exercise.intent.AddExerciseIntent
import com.lefarmico.create_new_exercise.viewModel.CreateNewExerciseViewModel
import com.lefarmico.domain.utils.DataState
import com.lefarmico.navigation.params.LibraryParams
import java.lang.IllegalArgumentException

class CreateNewExerciseFragment : BaseFragment<FragmentCreateNewExerciseBinding, CreateNewExerciseViewModel>(
    FragmentCreateNewExerciseBinding::inflate,
    CreateNewExerciseViewModel::class.java
) {

    private val params: LibraryParams.NewExercise by lazy {
        arguments?.getParcelable<LibraryParams.NewExercise>(KEY_PARAMS)
            ?: throw (IllegalArgumentException("Arguments params must be not null"))
    }

    override fun setUpViews() {
        binding.addButton.setOnClickListener {
            viewModel.onTriggerEvent(
                AddExerciseIntent.AddExerciseResult(
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
                    viewModel.onTriggerEvent(AddExerciseIntent.OnAddExerciseSuccess)
                }
            }
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
        return params.subCategoryId
    }

    companion object {

        private const val KEY_PARAMS = "new_exercise_params"

        fun createBundle(data: Parcelable?): Bundle {
            return Bundle().apply {
                when (data) {
                    is LibraryParams.NewExercise -> putParcelable(KEY_PARAMS, data)
                    else -> {
                        if (BuildConfig.DEBUG) {
                            throw (
                                IllegalArgumentException(
                                    "data should be LibraryParams.NewExercise type." +
                                        "but it's ${data!!.javaClass.simpleName}"
                                )
                                )
                        }
                    }
                }
            }
        }
    }
}
