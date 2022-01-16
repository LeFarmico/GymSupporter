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
import com.lefarmico.exercise_menu.databinding.FragmentSubcategoryListBinding
import com.lefarmico.exercise_menu.intent.SubcategoryIntent
import com.lefarmico.exercise_menu.intent.SubcategoryIntent.*
import com.lefarmico.exercise_menu.state.LibraryListEvent
import com.lefarmico.exercise_menu.state.LibraryListState
import com.lefarmico.exercise_menu.viewModel.SubcategoryViewModel
import com.lefarmico.navigation.params.LibraryParams

class SubcategoryFragment :
    BaseFragment<SubcategoryIntent, LibraryListState, LibraryListEvent,
        FragmentSubcategoryListBinding, SubcategoryViewModel>(
        FragmentSubcategoryListBinding::inflate,
        SubcategoryViewModel::class.java
    ) {

    private val adapter = LibraryItemAdapter()
    private val textFieldString get() = binding.editText.text.toString()
    private val decorator get() = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

    private val params: LibraryParams.SubcategoryList by lazy {
        arguments?.getParcelable<LibraryParams.SubcategoryList>(KEY_PARAMS)
            ?: throw (IllegalArgumentException("Arguments params must be not null"))
    }

    override fun setUpViews() {
        dispatchIntent(GetSubcategories(params.categoryId))
        isAddButtonShown(false)

        binding.apply {
            recycler.adapter = adapter
            recycler.addItemDecoration(decorator, 0)

            plusButton.setOnClickListener {
                dispatchIntent(AddSubcategory(textFieldString, params.categoryId))
                defaultStateEditText(editText)
            }
            editText.doOnTextChanged { text, _, _, _ ->
                dispatchIntent(Validate(text.toString()))
                isAddButtonActive(false)
            }
            editText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    dispatchIntent(Validate(editText.text.toString()))
                    isAddButtonShown(true)
                } else {
                    isAddButtonShown(false)
                }
            }
            editText.setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        defaultStateEditText(editText)
                        true
                    }
                    else -> false
                }
            }
        }
        adapter.onClick = { item ->
            item as LibraryViewData.SubCategory
            dispatchIntent(ClickItem(item, params.isFromWorkoutScreen))
        }
    }

    private fun showSubcategories(items: List<LibraryViewData>) {
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
        editText.clearFocus()
        editText.isCursorVisible = false
    }

    private fun setEditTextError(errorText: String) {
        binding.textField.error = errorText
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

    private fun isAddButtonShown(isShown: Boolean) {
        when (isShown) {
            true -> binding.plusButton.visibility = View.VISIBLE
            false -> binding.plusButton.visibility = View.GONE
        }
    }

    override fun receive(state: LibraryListState) {
        when (state) {
            is LibraryListState.ExceptionResult -> throw (state.exception)
            is LibraryListState.LibraryResult -> showSubcategories(state.libraryList)
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
                setEditTextError("That field is empty")
                isAddButtonActive(false)
            }
            LibraryListEvent.ValidationResult.Success -> {
                setEditTextError("")
                isAddButtonActive(true)
            }
        }
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
