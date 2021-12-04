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
import com.lefarmico.core.utils.ValidationState.*
import com.lefarmico.domain.utils.DataState
import com.lefarmico.exercise_menu.databinding.FragmentCategoriesBinding
import com.lefarmico.exercise_menu.intent.CategoryListIntent
import com.lefarmico.exercise_menu.intent.CategoryListIntent.*
import com.lefarmico.exercise_menu.viewModel.CategoryListViewModel
import com.lefarmico.navigation.params.LibraryParams

class CategoryListFragment :
    BaseFragment<FragmentCategoriesBinding, CategoryListViewModel>(
        FragmentCategoriesBinding::inflate,
        CategoryListViewModel::class.java
    ),
    CategoryListView {

    private val textFieldString get() = binding.editText.text.toString()
    private val decorator get() = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    private val adapter = ExerciseLibraryAdapter()

    private val params: LibraryParams.CategoryList by lazy {
        arguments?.getParcelable(KEY_PARAMS)
            ?: LibraryParams.CategoryList(isFromWorkoutScreen = false)
    }

    override fun setUpViews() {
        startEvent(GetCategories)

        binding.apply {
            adapter.onClick = { item ->
                item as LibraryViewData.Category
                startEvent(GoToSubcategoryScreen(item.id, params.isFromWorkoutScreen))
            }
            recycler.adapter = adapter
            recycler.addItemDecoration(decorator, 0)

            plusButton.setOnClickListener {
                startEvent(ValidateCategory(textFieldString))
                defaultStateEditText(editText)
            }
            editText.setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        startEvent(ValidateCategory(textFieldString))
                        defaultStateEditText(editText)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun observeData() {
        viewModel.notificationLiveData.observe(viewLifecycleOwner) { notification ->
            startEvent(ShowToast(notification))
        }
        viewModel.validationLiveData.observe(viewLifecycleOwner) { validationState ->
            when (validationState) {
                Empty -> startEvent(ShowToast("field should not be empty"))
                is Success -> startEvent(AddCategory(validationState.field))
                is AlreadyExist -> {
                    startEvent(ShowToast("${validationState.field} category already exist"))
                }
            }
        }
        viewModel.categoriesLiveData.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                DataState.Empty -> hideCategories()
                is DataState.Error -> {
                    adapter.items = mutableListOf()
                    binding.state.showErrorState()
                }
                DataState.Loading -> {
                    adapter.items = mutableListOf()
                    binding.state.showLoadingState()
                }
                is DataState.Success -> showCategories(dataState.data)
            }
        }
    }

    override fun showCategories(items: List<LibraryViewData.Category>) {
        adapter.items = items
        binding.state.showSuccessState()
    }

    override fun hideCategories() {
        adapter.items = mutableListOf()
        binding.state.showEmptyState()
    }

    private fun startEvent(event: CategoryListIntent) {
        viewModel.onTriggerEvent(event)
    }

    private fun defaultStateEditText(editText: EditText) {
        hideSoftKeyboard()
        editText.text!!.clear()
        editText.clearFocus()
        editText.isCursorVisible = false
    }

    companion object {
        private const val KEY_PARAMS = "category_key"

        fun createBundle(data: Parcelable?): Bundle {
            return Bundle().apply {
                when (data) {
                    is LibraryParams.CategoryList -> putParcelable(KEY_PARAMS, data)
                    else -> {
                        if (BuildConfig.DEBUG) {
                            throw (
                                IllegalBundleDataTypeException(
                                    "data should be LibraryParams.CategoryList type." +
                                        "but it's type ${data!!.javaClass.canonicalName}"
                                )
                                )
                        }
                    }
                }
            }
        }
    }
}
