package com.lefarmico.exercise_menu.view

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DividerItemDecoration
import com.lefarmico.core.BuildConfig
import com.lefarmico.core.adapter.LibraryItemAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.core.exceptions.IllegalBundleDataTypeException
import com.lefarmico.core.extensions.hideSoftKeyboard
import com.lefarmico.exercise_menu.action.CategoryAction
import com.lefarmico.exercise_menu.databinding.FragmentCategoriesBinding
import com.lefarmico.exercise_menu.intent.CategoryIntent
import com.lefarmico.exercise_menu.intent.CategoryIntent.*
import com.lefarmico.exercise_menu.state.LibraryListEvent
import com.lefarmico.exercise_menu.state.LibraryListState
import com.lefarmico.exercise_menu.viewModel.CategoryViewModel
import com.lefarmico.navigation.params.LibraryParams

class CategoryFragment :
    BaseFragment<
        CategoryIntent, CategoryAction, LibraryListState, LibraryListEvent,
        FragmentCategoriesBinding, CategoryViewModel>(
        FragmentCategoriesBinding::inflate,
        CategoryViewModel::class.java
    ) {

    private val textFieldString get() = binding.editText.text.toString()
    private val decorator get() = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    private val adapter = LibraryItemAdapter()

    private val params: LibraryParams.CategoryList by lazy {
        arguments?.getParcelable(KEY_PARAMS)
            ?: LibraryParams.CategoryList(isFromWorkoutScreen = false)
    }

    override fun setUpViews() {
        dispatchIntent(GetCategories)

        binding.apply {
            adapter.onClick = { item ->
                val category = item as LibraryViewData.Category
                dispatchIntent(ClickItem(category, params.isFromWorkoutScreen))
            }
            recycler.adapter = adapter
            recycler.addItemDecoration(decorator, 0)

            plusButton.setOnClickListener {
                dispatchIntent(AddCategory(textFieldString))
                defaultStateEditText(editText)
            }
            editText.doOnTextChanged { text, _, _, _ ->
                dispatchIntent(Validate(text.toString()))
            }

            editText.setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
//                        dispatchIntent(Validate(textFieldString))
                        defaultStateEditText(editText)
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun showCategories(items: List<LibraryViewData>) {
        adapter.items = items
        if (items.isEmpty()) {
            binding.state.showEmptyState()
            return
        }
        binding.state.showSuccessState()
    }

    private fun showLoading() {
        binding.state.showLoadingState()
    }

    private fun defaultStateEditText(editText: EditText) {
        hideSoftKeyboard()
        editText.text!!.clear()
        editText.clearFocus()
        editText.isCursorVisible = false
    }

    private fun isAddButtonActive(isActive: Boolean) {
        if (!isActive) {
            binding.plusButton.alpha = 0.5f
            binding.plusButton.isClickable = false
            binding.plusButton.focusable = View.NOT_FOCUSABLE
            return
        }
        binding.plusButton.alpha = 1f
        binding.plusButton.isClickable = true
        binding.plusButton.focusable = View.FOCUSABLE
    }

    private fun setEditTextError(errorText: String) {
        binding.editText.error = errorText
    }

    override fun receive(state: LibraryListState) {
        when (state) {
            is LibraryListState.ExceptionResult -> throw (state.exception)
            is LibraryListState.LibraryResult -> showCategories(state.libraryList)
            LibraryListState.Loading -> showLoading()
        }
    }

    override fun receive(event: LibraryListEvent) {
        when (event) {
            is LibraryListEvent.ShowToast -> {}
            LibraryListEvent.ValidationResult.AlreadyExist -> {
                setEditTextError("That field already exist")
                isAddButtonActive(false)
            }
            LibraryListEvent.ValidationResult.Empty -> {
                setEditTextError("That field already exist")
                isAddButtonActive(false)
            }
            LibraryListEvent.ValidationResult.Success -> {
                setEditTextError("")
                isAddButtonActive(true)
            }
        }
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
