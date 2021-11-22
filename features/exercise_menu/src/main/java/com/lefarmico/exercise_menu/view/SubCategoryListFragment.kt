package com.lefarmico.exercise_menu.view

import android.os.Bundle
import android.os.Parcelable
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.recyclerview.widget.DividerItemDecoration
import com.lefarmico.core.BuildConfig
import com.lefarmico.core.adapter.ExerciseLibraryAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.core.exceptions.IllegalBundleDataTypeException
import com.lefarmico.core.extensions.hideSoftKeyboard
import com.lefarmico.core.utils.ValidationState
import com.lefarmico.domain.utils.DataState
import com.lefarmico.exercise_menu.databinding.FragmentSubcategoryListBinding
import com.lefarmico.exercise_menu.intent.SubCategoryIntent
import com.lefarmico.exercise_menu.intent.SubCategoryIntent.*
import com.lefarmico.exercise_menu.viewModel.SubCategoryViewModel
import com.lefarmico.navigation.params.LibraryParams
import java.lang.IllegalArgumentException

class SubCategoryListFragment : BaseFragment<FragmentSubcategoryListBinding, SubCategoryViewModel>(
    FragmentSubcategoryListBinding::inflate,
    SubCategoryViewModel::class.java
) {

    private val adapter = ExerciseLibraryAdapter()
    private val textFieldString get() = binding.editText.text.toString()
    private val decorator get() = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

    private val params: LibraryParams.SubcategoryList by lazy {
        arguments?.getParcelable<LibraryParams.SubcategoryList>(KEY_PARAMS)
            ?: throw (IllegalArgumentException("Arguments params must be not null"))
    }

    override fun setUpViews() {
        startEvent(GetSubcategories(params.categoryId))

        binding.apply {
            recycler.adapter = adapter
            recycler.addItemDecoration(decorator, 0)

            plusButton.setOnClickListener {
                binding.textField.apply {
                    startEvent(ValidateSubcategory(textFieldString, params.categoryId))
                }
            }
            editText.setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        startEvent(ValidateSubcategory(textFieldString, params.categoryId))
                        defaultStateEditText(editText)
                        true
                    }
                    else -> false
                }
            }
        }
        adapter.onClick = { item ->
            item as LibraryViewData.SubCategory
            startEvent(
                GoToExerciseListScreen(params.categoryId, item.id, params.isFromWorkoutScreen)
            )
        }
    }

    override fun observeData() {
        viewModel.notificationLiveData.observe(viewLifecycleOwner) { notification ->
            startEvent(ShowToast(notification))
        }
        viewModel.validationLiveData.observe(viewLifecycleOwner) { validationState ->
            when (validationState) {
                ValidationState.Empty -> startEvent(ShowToast("field should not be empty"))

                is ValidationState.Success -> {
                    startEvent(AddSubcategory(validationState.field, params.categoryId))
                }
                is ValidationState.AlreadyExist -> {
                    startEvent(ShowToast("${validationState.field} subcategory already exist"))
                }
            }
        }
        viewModel.subCategoriesLiveData.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                DataState.Empty -> {
                    adapter.items = mutableListOf()
                    binding.state.showEmptyState()
                }
                is DataState.Error -> {
                    adapter.items = mutableListOf()
                    binding.state.showErrorState()
                }
                DataState.Loading -> {
                    adapter.items = mutableListOf()
                    binding.state.showLoadingState()
                }
                is DataState.Success -> {
                    adapter.items = dataState.data
                    binding.state.showSuccessState()
                }
            }
        }
    }

    private fun startEvent(event: SubCategoryIntent) {
        viewModel.onTriggerEvent(event)
    }

    private fun defaultStateEditText(editText: EditText) {
        hideSoftKeyboard()
        editText.text!!.clear()
        editText.clearFocus()
        editText.isCursorVisible = false
    }

    companion object {
        private const val KEY_PARAMS = "subcategory_key"

        fun createBundle(data: Parcelable?): Bundle {
            return Bundle().apply {
                when (data) {
                    is LibraryParams.SubcategoryList -> putParcelable(KEY_PARAMS, data)
                    else -> {
                        if (BuildConfig.DEBUG) {
                            throw (
                                IllegalBundleDataTypeException(
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
