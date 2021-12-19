package com.lefarmico.exercise_menu.view

import android.os.Bundle
import android.os.Parcelable
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.recyclerview.widget.DividerItemDecoration
import com.lefarmico.core.BuildConfig
import com.lefarmico.core.adapter.LibraryItemAdapter
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

class SubCategoryListFragment :
    BaseFragment<FragmentSubcategoryListBinding, SubCategoryViewModel>(
        FragmentSubcategoryListBinding::inflate,
        SubCategoryViewModel::class.java
    ),
    SubCategoriesListView {

    private val adapter = LibraryItemAdapter()
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
                startEvent(ValidateSubcategory(textFieldString, params.categoryId))
                defaultStateEditText(editText)
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
            val id = item.id
            startEvent(
                GoToExerciseListScreen(params.categoryId, id, params.isFromWorkoutScreen)
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
                DataState.Empty -> hideSubcategories()
                is DataState.Error -> {
                    adapter.items = mutableListOf()
                    binding.state.showErrorState()
                }
                DataState.Loading -> {
                    adapter.items = mutableListOf()
                    binding.state.showLoadingState()
                }
                is DataState.Success -> showSubcategories(dataState.data)
            }
        }
    }

    override fun showSubcategories(items: List<LibraryViewData.SubCategory>) {
        adapter.items = items
        binding.state.showSuccessState()
    }

    override fun hideSubcategories() {
        adapter.items = mutableListOf()
        binding.state.showEmptyState()
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
