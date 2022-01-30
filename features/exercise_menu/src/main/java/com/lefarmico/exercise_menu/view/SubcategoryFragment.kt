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
import com.lefarmico.exercise_menu.databinding.FragmentSubcategoryListBinding
import com.lefarmico.exercise_menu.intent.SubcategoryIntent
import com.lefarmico.exercise_menu.intent.SubcategoryIntent.*
import com.lefarmico.exercise_menu.intent.SubcategoryIntent.EditState.Action.*
import com.lefarmico.exercise_menu.state.LibraryListEvent
import com.lefarmico.exercise_menu.state.LibraryListState
import com.lefarmico.exercise_menu.viewModel.SubcategoryViewModel
import com.lefarmico.navigation.params.LibraryParams
import java.lang.Exception

class SubcategoryFragment :
    BaseFragment<SubcategoryIntent, LibraryListState, LibraryListEvent,
        FragmentSubcategoryListBinding, SubcategoryViewModel>(
        FragmentSubcategoryListBinding::inflate,
        SubcategoryViewModel::class.java
    ) {

    private val adapter = LibraryItemAdapter()
    private val textFieldString get() = binding.editText.text.toString()
    private val decorator get() = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

    private var actionMode: ActionMode? = null
    private var selectHandler: SelectItemsHandler<LibraryViewData>? = null
    private var actionModeCallback: EditStateActionBarCallback? = null

    private val params: LibraryParams.SubcategoryList by lazy {
        arguments?.getParcelable<LibraryParams.SubcategoryList>(KEY_PARAMS)
            ?: throw (IllegalArgumentException("Arguments params must be not null"))
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
        dispatchIntent(GetSubcategories(params.categoryId))
        dispatchIntent(GetCategotyTitle(params.categoryId))
        isAddButtonShown(false)

        actionModeCallback = object : EditStateActionBarCallback() {
            override fun selectAllButtonHandler() { dispatchIntent(EditState(SelectAll)) }
            override fun removeButtonHandler() { dispatchIntent(EditState(DeleteSelected)) }
            override fun onDestroyHandler() { dispatchIntent(EditState(Hide)) }
        }

        selectHandler = object : SelectItemsHandler<LibraryViewData>(adapter) {
            override fun selectedItemAction(item: LibraryViewData) {
                require(item is LibraryViewData.SubCategory)
                dispatchIntent(DeleteSubCategory(item.id, item.categoryId))
            }
        }

        binding.apply {
            recycler.adapter = adapter
            recycler.addItemDecoration(decorator, 0)

            plusButton.setOnClickListener {
                dispatchIntent(AddSubcategory(textFieldString, params.categoryId))
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
        adapter.onClick = { item ->
            check(item is LibraryViewData.SubCategory)
            dispatchIntent(ClickItem(item, params.isFromWorkoutScreen))
        }
    }

    override fun receive(state: LibraryListState) {
        when (state) {
            is LibraryListState.LibraryResult -> showSubcategories(state.libraryList)
            LibraryListState.Loading -> showLoading()
            is LibraryListState.Title -> setTitle(state.title)
        }
    }

    override fun receive(event: LibraryListEvent) {
        when (event) {
            LibraryListEvent.ValidationResult.AlreadyExist -> {
                setEditTextError { "That field already exist" }
                isAddButtonActive(false)
            }
            LibraryListEvent.ValidationResult.Empty -> {
                setEditTextError { "That field is empty" }
                isAddButtonActive(false)
            }
            LibraryListEvent.ValidationResult.Success -> {
                setEditTextError { "" }
                isAddButtonActive(true)
            }
            LibraryListEvent.DeleteSelectedWorkouts -> deleteSelectedWorkouts()
            LibraryListEvent.HideEditState -> hideEditState()
            LibraryListEvent.SelectAllWorkouts -> selectAllWorkouts()
            LibraryListEvent.ShowEditState -> showEditState()
            LibraryListEvent.DeselectAllWorkouts -> TODO()
            is LibraryListEvent.ExceptionResult -> onExceptionResult(event.exception)
        }
    }

    private fun setTitle(title: String) {
        requireActivity().title = title
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun showSubcategories(items: List<LibraryViewData>) {
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
        editText.error = getString(R.string.input_field_success)
        editText.text.clear()
    }

    private inline fun setEditTextError(errorText: () -> String) {
        binding.textField.error = errorText()
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

    private fun onExceptionResult(exception: Exception) {
        // TODO send log to crashlytics
        dispatchIntent(ShowToast(getString(R.string.state_error)))
    }

    companion object {
        private const val KEY_PARAMS = "subcategory_key"

        fun createBundle(data: Parcelable?): Bundle {
            requireNotNull(data)
            require(data is LibraryParams.SubcategoryList)
            return Bundle().apply { putParcelable(KEY_PARAMS, data) }
        }
    }
}
