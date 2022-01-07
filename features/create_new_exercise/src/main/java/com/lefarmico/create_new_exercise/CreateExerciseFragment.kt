package com.lefarmico.create_new_exercise

import android.os.Bundle
import android.os.Parcelable
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.core.exceptions.IllegalBundleDataTypeException
import com.lefarmico.core.utils.EmptyTextValidator
import com.lefarmico.create_new_exercise.CreateExerciseIntent.*
import com.lefarmico.create_new_exercise.databinding.FragmentCreateNewExerciseBinding
import com.lefarmico.navigation.params.LibraryParams
import java.lang.IllegalArgumentException

class CreateExerciseFragment : BaseFragment<
    CreateExerciseIntent, CreateExerciseAction, CreateExerciseState, CreateExerciseEvent,
    FragmentCreateNewExerciseBinding, CreateExerciseViewModel>(
    FragmentCreateNewExerciseBinding::inflate,
    CreateExerciseViewModel::class.java
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
        dispatchIntent(GetExercises(params.subCategoryId))

        binding.apply {
            exerciseEditText.addTextChangedListener(
                EmptyTextValidator(exerciseEditText, exerciseTitleTextView)
            )
            addButton.setOnClickListener {
                dispatchIntent(ValidateExercise(textField))
            }
        }
    }

    override fun receive(state: CreateExerciseState) {
        when (state) {
            is CreateExerciseState.ExceptionResult -> throw (state.exception)
            CreateExerciseState.Loading -> {}
        }
    }

    override fun receive(event: CreateExerciseEvent) {
        when (event) {
            CreateExerciseEvent.ValidationAlreadyExist ->
                dispatchIntent(ShowToast("that exercise ia already exist."))
            CreateExerciseEvent.ValidationEmpty ->
                dispatchIntent(ShowToast("field should not be empty."))
            CreateExerciseEvent.ValidationSuccess ->
                dispatchIntent(AddExercise(textField, descriptionField, imageSourceField, subcategory))
        }
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
