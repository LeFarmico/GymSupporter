package com.lefarmico.exercise_menu.view

import android.os.Bundle
import android.os.Parcelable
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DividerItemDecoration
import com.lefarmico.core.adapter.LibraryItemAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.core.extensions.hideSoftKeyboard
import com.lefarmico.core.selector.SelectItemsHandler
import com.lefarmico.core.toolbar.EditStateActionBarCallback
import com.lefarmico.exercise_menu.R
import com.lefarmico.exercise_menu.databinding.FragmentCategoriesBinding
import com.lefarmico.exercise_menu.intent.CategoryIntent
import com.lefarmico.exercise_menu.intent.CategoryIntent.*
import com.lefarmico.exercise_menu.intent.CategoryIntent.EditState.Action.*
import com.lefarmico.exercise_menu.state.LibraryListEvent
import com.lefarmico.exercise_menu.state.LibraryListState
import com.lefarmico.exercise_menu.viewModel.CategoryViewModel
import com.lefarmico.navigation.params.LibraryParams

class CategoryFragment :
    BaseFragment<CategoryIntent, LibraryListState, LibraryListEvent,
        FragmentCategoriesBinding, CategoryViewModel>(
        FragmentCategoriesBinding::inflate,
        CategoryViewModel::class.java
    ) {

    private val textFieldString get() = binding.editText.text.toString()
    private val decorator get() = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    private val adapter = LibraryItemAdapter()

    private var actionMode: ActionMode? = null
    private var selectHandler: SelectItemsHandler<LibraryViewData>? = null
    private var actionModeCallback: EditStateActionBarCallback? = null

    private val params: LibraryParams.CategoryList by lazy {
        arguments?.getParcelable(KEY_PARAMS)
            ?: LibraryParams.CategoryList(isFromWorkoutScreen = false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit -> {
                dispatchIntent(EditState(Show))
                true
            }
            else -> false
        }
    }

    override fun setUpViews() {
        dispatchIntent(GetCategories)
        isAddButtonShown(false)
        setUpToolbar(params.isFromWorkoutScreen)

        actionModeCallback = object : EditStateActionBarCallback() {
            override fun selectAllButtonHandler() { dispatchIntent(EditState(SelectAll)) }
            override fun removeButtonHandler() { dispatchIntent(EditState(DeleteSelected)) }
            override fun onDestroyHandler() { dispatchIntent(EditState(Hide)) }
        }
        selectHandler = object : SelectItemsHandler<LibraryViewData>(adapter) {
            override fun selectedItemAction(item: LibraryViewData) {
                require(item is LibraryViewData.Category)
                dispatchIntent(DeleteCategory(item.id))
            }
        }

        binding.apply {
            adapter.onClick = { category ->
                require(category is LibraryViewData.Category)
                dispatchIntent(ClickItem(category, params.isFromWorkoutScreen))
            }
            recycler.adapter = adapter
            recycler.addItemDecoration(decorator, 0)

            plusButton.setOnClickListener {
                dispatchIntent(AddCategory(textFieldString))
                defaultStateEditText(editText)
                clearEditTextField(editText)
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
    }

    override fun receive(state: LibraryListState) {
        when (state) {
            is LibraryListState.ExceptionResult -> onExceptionResult()
            is LibraryListState.LibraryResult -> showCategories(state.libraryList)
            LibraryListState.Loading -> showLoading()
            else -> {}
        }
    }

    override fun receive(event: LibraryListEvent) {
        when (event) {
            LibraryListEvent.ValidationResult.AlreadyExist -> {
                setEditTextError(getString(R.string.input_field_exist))
                isAddButtonActive(false)
            }
            LibraryListEvent.ValidationResult.Empty -> {
                setEditTextError(getString(R.string.input_field_empty))
                isAddButtonActive(false)
            }
            LibraryListEvent.ValidationResult.Success -> {
                setEditTextError(getString(R.string.input_field_success))
                isAddButtonActive(true)
            }
            LibraryListEvent.DeleteSelectedWorkouts -> {
                deleteSelectedWorkouts()
            }
            LibraryListEvent.DeselectAllWorkouts -> TODO()
            LibraryListEvent.HideEditState -> hideEditState()
            LibraryListEvent.SelectAllWorkouts -> selectAllWorkouts()
            LibraryListEvent.ShowEditState -> showEditState()
            is LibraryListEvent.ExceptionEvent -> onExceptionResult()
        }
    }

    private fun showCategories(items: List<LibraryViewData>) {
        adapter.items = items
        when (items.isEmpty()) {
            true -> binding.state.showEmptyState()
            false -> binding.state.showSuccessState()
        }
    }

    private fun showLoading() {
        binding.state.showLoadingState()
    }

    private fun clearEditTextField(editText: EditText) {
        editText.text.clear()
    }
    private fun defaultStateEditText(editText: EditText) {
        hideSoftKeyboard()
        editText.clearFocus()
        editText.isCursorVisible = false
    }

    private fun isAddButtonActive(isActive: Boolean) {
        when (isActive) {
            true -> {
                binding.plusButton.alpha = 1f
                binding.plusButton.isClickable = true
                binding.plusButton.focusable = View.FOCUSABLE
            }
            false -> {
                binding.plusButton.alpha = 0.5f
                binding.plusButton.isClickable = false
                binding.plusButton.focusable = View.NOT_FOCUSABLE
            }
        }
    }

    private fun isAddButtonShown(isShown: Boolean) {
        when (isShown) {
            true -> binding.plusButton.visibility = View.VISIBLE
            false -> binding.plusButton.visibility = View.GONE
        }
    }

    private fun setEditTextError(errorText: String) {
        binding.textField.error = errorText
    }

    private fun showEditState() {
        adapter.turnOnEditState()
        actionMode = requireActivity().startActionMode(actionModeCallback)
    }

    private fun hideEditState() {
        adapter.turnOffEditState()
        actionMode?.finish()
    }

    private fun deleteSelectedWorkouts() {
        selectHandler?.onEachSelectedItemsAction()
        actionMode?.finish()
    }

    private fun selectAllWorkouts() {
        adapter.toggleSelectAll()
    }

    private fun setUpToolbar(isFromWorkoutScreen: Boolean) {
        requireActivity().title = getString(R.string.category_screen)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(
            isFromWorkoutScreen
        )
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(
            isFromWorkoutScreen
        )
    }

    private fun onExceptionResult() {
        // TODO send log to crashlytics
        dispatchIntent(ShowToast(getString(R.string.state_error)))
    }

    companion object {
        private const val KEY_PARAMS = "category_key"

        fun createBundle(data: Parcelable?): Bundle {
            requireNotNull(data)
            require(data is LibraryParams.CategoryList)
            return Bundle().apply { putParcelable(KEY_PARAMS, data) }
        }
    }
}
