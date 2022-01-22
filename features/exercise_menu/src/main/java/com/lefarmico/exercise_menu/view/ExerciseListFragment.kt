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
import com.lefarmico.exercise_menu.intent.ExerciseIntent
import com.lefarmico.exercise_menu.intent.ExerciseIntent.*
import com.lefarmico.exercise_menu.intent.ExerciseIntent.EditState.Action.*
import com.lefarmico.exercise_menu.state.LibraryListEvent
import com.lefarmico.exercise_menu.state.LibraryListState
import com.lefarmico.exercise_menu.viewModel.ExerciseListViewModel
import com.lefarmico.navigation.params.LibraryParams

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

    override fun receive(state: LibraryListState) {
        when (state) {
            is LibraryListState.ExceptionResult -> throw (state.exception)
            is LibraryListState.LibraryResult -> showExercises(state.libraryList)
            LibraryListState.Loading -> showLoading()
            is LibraryListState.Title -> setTitle(state.title)
        }
    }

    private fun setTitle(title: String) {
        requireActivity().title = title
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun receive(event: LibraryListEvent) {
        when (event) {
            is LibraryListEvent.ShowToast -> {}
            LibraryListEvent.DeselectAllWorkouts -> {}
            LibraryListEvent.DeleteSelectedWorkouts -> deleteSelectedWorkouts()
            LibraryListEvent.HideEditState -> hideEditState()
            LibraryListEvent.SelectAllWorkouts -> selectAllWorkouts()
            LibraryListEvent.ShowEditState -> showEditState()
            else -> throw IllegalStateException()
        }
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
