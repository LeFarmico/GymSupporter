package com.lefarmico.home

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearSnapHelper
import com.lefarmico.core.adapter.CalendarAdapter
import com.lefarmico.core.adapter.WorkoutRecordsAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.core.decorator.ItemSpaceDecoration
import com.lefarmico.core.entity.CalendarItemViewData
import com.lefarmico.core.entity.WorkoutRecordsViewData.WorkoutWithExercisesAndSets
import com.lefarmico.core.selector.SelectItemsHandler
import com.lefarmico.core.toolbar.RemoveActionBarCallback
import com.lefarmico.home.HomeIntent.*
import com.lefarmico.home.databinding.FragmentHomeBinding
import java.time.LocalDate

class HomeFragment :
    BaseFragment<HomeIntent, HomeAction, HomeState, HomeEvent,
        FragmentHomeBinding,
        HomeViewModel>(
        FragmentHomeBinding::inflate,
        HomeViewModel::class.java
    ) {

    private var actionMode: ActionMode? = null
    private var selectHandler: SelectItemsHandler<WorkoutWithExercisesAndSets>? = null
    private var actionModeCallback: RemoveActionBarCallback? = null

    private val noteAdapter = WorkoutRecordsAdapter()
    private val calendarAdapter = CalendarAdapter(LocalDate.now())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun setUpViews() {
        dispatchIntent(CurrentMonth)
        dispatchIntent(GetWorkoutRecordsByCurrentDate)
        dispatchIntent(GetDaysInMonth)

        binding.apply {
            prevMonthButton.setOnClickListener { dispatchIntent(PrevMonth) }
            nextMonthButton.setOnClickListener { dispatchIntent(NextMonth) }
            newWorkoutButton.setOnClickListener { dispatchIntent(NavigateToWorkoutScreen) }

            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(calendar)

            val decoratorHr = ItemSpaceDecoration(1, ItemSpaceDecoration.HORIZONTAL)
            val decoratorVert = ItemSpaceDecoration(4, ItemSpaceDecoration.VERTICAL)
            calendar.addItemDecoration(decoratorHr, 0)
            calendar.adapter = calendarAdapter.apply {
                clickListener = { localDate -> dispatchIntent(ClickDate(localDate)) }
            }
            workoutNotes.addItemDecoration(decoratorVert, 0)
            workoutNotes.adapter = noteAdapter.apply {
                onEditButtonAction = { workoutRecordsVD ->
                    dispatchIntent(NavigateToDetailsWorkoutScreen(workoutRecordsVD.id))
                }
            }
        }

        actionModeCallback = object : RemoveActionBarCallback() {
            override fun selectAllButtonHandler() {
                dispatchIntent(SelectAllWorkouts)
            }
            override fun removeButtonHandler() {
                dispatchIntent(DeleteSelectedWorkouts)
            }
            override fun onDestroyHandler() {
                dispatchIntent(HideEditState)
            }
        }
        selectHandler = object : SelectItemsHandler<WorkoutWithExercisesAndSets>(noteAdapter) {
            override fun selectedItemAction(item: WorkoutWithExercisesAndSets) {
                dispatchIntent(DeleteWorkout(item.workout.id))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit -> {
                dispatchIntent(ShowEditState)
                true
            }
            else -> { false }
        }
    }

    private fun showLoading() {
        binding.state.showLoadingState()
    }

    private fun showSuccess() {
        binding.state.showSuccessState()
    }

    private fun showEmptyState() {
        binding.state.showEmptyState()
    }

    private fun showWorkouts(items: List<WorkoutWithExercisesAndSets>) {
        if (items.isNotEmpty()) {
            showSuccess()
        } else {
            showEmptyState()
        }
        noteAdapter.items = items
    }

    private fun showCalendar(items: List<CalendarItemViewData>) {
        calendarAdapter.items = items.toMutableList()
        recyclerScrollToPos(items, LocalDate.now())
    }

    private fun showCurrentMonthAndYear(month: MonthAndYearText) {
        binding.currentMonth.text = month.text
    }

    private fun switchEditState(isOn: Boolean) {
        if (!isOn) {
            noteAdapter.turnOffEditState()
            binding.calendar.visibility = View.VISIBLE
            binding.nextMonthButton.isEnabled = true
            binding.prevMonthButton.isEnabled = true
            binding.newWorkoutButton.isEnabled = true
            actionMode?.finish()
            return
        }
        noteAdapter.turnOnEditState()
        binding.calendar.visibility = View.GONE
        binding.nextMonthButton.isEnabled = false
        binding.prevMonthButton.isEnabled = false
        binding.newWorkoutButton.isEnabled = false
        actionMode = requireActivity().startActionMode(actionModeCallback)
    }

    private fun selectAllWorkouts() {
        noteAdapter.toggleSelectAll()
    }

    private fun deleteSelectedWorkouts() {
        selectHandler?.onEachSelectedItemsAction()
        actionMode?.finish()
    }

    private fun recyclerScrollToPos(list: List<CalendarItemViewData>, date: LocalDate) {
        val pos = list.indexOfFirst { it.date == date }
        binding.calendar.scrollToPosition(pos - 1)
    }

    override fun receive(event: HomeEvent) {
        when (event) {
            HomeEvent.DeleteSelectedWorkouts -> deleteSelectedWorkouts()
            HomeEvent.SelectAllWorkouts -> selectAllWorkouts()
            HomeEvent.DeselectAllWorkouts -> TODO()
            HomeEvent.HideEditState -> switchEditState(false)
            HomeEvent.ShowEditState -> switchEditState(true)
        }
    }

    override fun receive(state: HomeState) {
        when (state) {
            is HomeState.CalendarResult -> showCalendar(state.calendarItemList)
            is HomeState.ExceptionResult -> throw (state.exception)
            HomeState.Loading -> showLoading()
            is HomeState.MonthAndYearResult -> showCurrentMonthAndYear(state.monthAndYearText)
            is HomeState.WorkoutResult -> showWorkouts(state.workoutList)
        }
    }
}
