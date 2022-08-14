package com.lefarmico.exercise_menu.view

import android.os.Bundle
import android.os.Parcelable
import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.lefarmico.core.adapter.LibraryItemAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.core.selector.SelectItemsHandler
import com.lefarmico.core.toolbar.EditStateActionBarCallback
import com.lefarmico.exercise_menu.R
import com.lefarmico.exercise_menu.databinding.FragmentExerciseListBinding
import com.lefarmico.exercise_menu.intent.CategoryIntent
import com.lefarmico.exercise_menu.intent.ExerciseIntent
import com.lefarmico.exercise_menu.intent.ExerciseIntent.*
import com.lefarmico.exercise_menu.intent.ExerciseIntent.EditState.Action.*
import com.lefarmico.exercise_menu.state.LibraryListEvent
import com.lefarmico.exercise_menu.state.LibraryListState
import com.lefarmico.exercise_menu.viewModel.ExerciseListViewModel
import com.lefarmico.navigation.params.LibraryParams
import java.lang.Exception

class ExerciseListFragment :
    BaseFragment<
        ExerciseIntent, LibraryListState, LibraryListEvent,
        FragmentExerciseListBinding, ExerciseListViewModel>(
        FragmentExerciseListBinding::inflate,
        ExerciseListViewModel::class.java
    ) {

    private val params: LibraryParams.ExerciseList by lazy {
        arguments?.getParcelable<LibraryParams.ExerciseList>(KEY_PARAMS)
            ?: throw (IllegalArgumentException("Arguments params must be not null"))
    }

    private val adapter = LibraryItemAdapter()

    private var actionMode: ActionMode? = null
    private var selectHandler: SelectItemsHandler<LibraryViewData>? = null
    private var actionModeCallback: EditStateActionBarCallback? = null

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
        dispatchIntent(GetExercises(params.subCategoryId))
        dispatchIntent(GetSubcategoryTitle(params.subCategoryId))

        adapter.onClick = { exercise ->
            require(exercise is LibraryViewData.Exercise)
            dispatchIntent(ClickItem(exercise, params.isFromWorkoutScreen))
        }
        binding.recycler.adapter = adapter

        actionModeCallback = object : EditStateActionBarCallback() {
            override fun selectAllButtonHandler() { dispatchIntent(EditState(SelectAll)) }
            override fun removeButtonHandler() { dispatchIntent(EditState(DeleteSelected)) }
            override fun onDestroyHandler() { dispatchIntent(EditState(Hide)) }
        }

        selectHandler = object : SelectItemsHandler<LibraryViewData>(adapter) {
            override fun selectedItemAction(item: LibraryViewData) {
                require(item is LibraryViewData.Exercise)
                dispatchIntent(DeleteExercise(item.id, item.subCategoryId))
            }
        }

        val decorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.recycler.addItemDecoration(decorator, 0)

        binding.plusButton.setOnClickListener {
            dispatchIntent(CreateNewExercise(params.subCategoryId, params.isFromWorkoutScreen))
        }
    }

    override fun receive(state: LibraryListState) {
        when (state) {
            is LibraryListState.LibraryResult -> showExercises(state.libraryList)
            LibraryListState.Loading -> showLoading()
            is LibraryListState.Title -> setTitle(state.title)
        }
    }

    override fun receive(event: LibraryListEvent) {
        when (event) {
            LibraryListEvent.DeselectAllWorkouts -> {}
            LibraryListEvent.DeleteSelectedWorkouts -> deleteSelectedWorkouts()
            LibraryListEvent.HideEditState -> hideEditState()
            LibraryListEvent.SelectAllWorkouts -> selectAllWorkouts()
            LibraryListEvent.ShowEditState -> showEditState()
            else -> onExceptionResult(IllegalArgumentException())
        }
    }

    private fun setTitle(title: String) {
        requireActivity().title = title
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun showExercises(items: List<LibraryViewData>) {
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
        adapter.turnOffEditState()
        actionMode?.finish()
    }

    private fun selectAllWorkouts() {
        adapter.toggleSelectAll()
    }

    private fun onExceptionResult(exception: Exception) {
        // TODO send log to crashlytics
        dispatchIntent(ShowToast(getString(R.string.state_error)))
    }

    override fun onPause() {
        super.onPause()
        actionMode?.finish()
    }

    companion object {
        private const val KEY_PARAMS = "exercise_key"

        fun createBundle(data: Parcelable?): Bundle {
            requireNotNull(data)
            require(data is LibraryParams.ExerciseList)
            return Bundle().apply { putParcelable(KEY_PARAMS, data) }
        }
    }
}
