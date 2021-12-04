package com.lefarmico.create_new_exercise.view

import android.os.Bundle
import android.os.Parcelable
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.core.exceptions.IllegalBundleDataTypeException
import com.lefarmico.core.utils.EmptyTextValidator
import com.lefarmico.core.utils.ValidationState
import com.lefarmico.create_new_exercise.BuildConfig
import com.lefarmico.create_new_exercise.databinding.FragmentCreateNewExerciseBinding
import com.lefarmico.create_new_exercise.intent.CreateExerciseIntent
import com.lefarmico.create_new_exercise.intent.CreateExerciseIntent.*
import com.lefarmico.create_new_exercise.viewModel.CreateNewExerciseViewModel
import com.lefarmico.navigation.params.LibraryParams
import java.lang.IllegalArgumentException

class CreateNewExerciseFragment : BaseFragment<FragmentCreateNewExerciseBinding, CreateNewExerciseViewModel>(
    FragmentCreateNewExerciseBinding::inflate,
    CreateNewExerciseViewModel::class.java
) {

    private val textField get() = binding.exerciseEditText.text.toString()
    private val descriptionField get() = binding.descriptionEditText.text.toString()
    private val imageSourceField get() = ""
    private val subcategory get() = params.subCategoryId

    private val params: LibraryParams.NewExercise by lazy {
        arguments?.getParcelable<LibraryParams.NewExercise>(KEY_PARAMS)
            ?: throw (IllegalArgumentException("Arguments params must be not null"))
    }

    override fun setUpViews() {
        binding.apply {
            exerciseEditText.addTextChangedListener(
                EmptyTextValidator(exerciseEditText, exerciseTitleTextView)
            )
            addButton.setOnClickListener {
                startEvent(ValidateExercise(textField, subcategory))
            }
        }
    }

    override fun observeData() {
        viewModel.notificationLiveData.observe(viewLifecycleOwner) { s ->
            startEvent(ShowToast(s))
        }
        viewModel.validationLiveData.observe(viewLifecycleOwner) { validationState ->
            when (validationState) {
                ValidationState.Empty -> startEvent(ShowToast("field should not be empty"))

                is ValidationState.Success -> {
                    startEvent(AddExercise(validationState.field, descriptionField, imageSourceField, subcategory))
                }
                is ValidationState.AlreadyExist -> {
                    startEvent(ShowToast("${validationState.field} exercise already exist"))
                }
            }
        }
    }

    private fun startEvent(event: CreateExerciseIntent) {
        viewModel.onTriggerEvent(event)
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
                                IllegalBundleDataTypeException(
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
