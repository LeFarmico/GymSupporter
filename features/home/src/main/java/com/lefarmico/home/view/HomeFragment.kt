package com.lefarmico.home.view

import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearSnapHelper
import com.lefarmico.core.adapter.CalendarAdapter
import com.lefarmico.core.adapter.WorkoutRecordsAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.core.entity.CalendarItemViewData
import com.lefarmico.core.entity.WorkoutRecordsViewData.*
import com.lefarmico.core.selector.SelectItemsHandler
import com.lefarmico.core.toolbar.EditActionBarEvents.*
import com.lefarmico.core.toolbar.RemoveActionBarCallback
import com.lefarmico.domain.utils.DataState
import com.lefarmico.home.R
import com.lefarmico.home.databinding.FragmentHomeBinding
import com.lefarmico.home.intent.HomeEvents
import com.lefarmico.home.intent.HomeIntent
import com.lefarmico.home.intent.HomeIntent.*
import com.lefarmico.home.viewModel.HomeViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.Exception

class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(
        FragmentHomeBinding::inflate,
        HomeViewModel::class.java
    ),
    HomeView {

    private var actionMode: ActionMode? = null
    private var selectHandler: SelectItemsHandler<WorkoutWithExercisesAndSets>? = null
    private var actionModeCallback: RemoveActionBarCallback? = null

    private var localDateTime = LocalDate.now().atStartOfDay()

    private val noteAdapter = WorkoutRecordsAdapter()
    private val calendarAdapter = CalendarAdapter(localDateTime)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun setUpViews() {
        setUpCalendar(localDateTime)

        binding.apply {
            prevMonthButton.setOnClickListener { turnToPrevMonth() }
            nextMonthButton.setOnClickListener { turnToNextMonth() }
            newWorkoutButton.setOnClickListener { startNewWorkout() }

            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(calendar)
            calendar.adapter = calendarAdapter.apply {
                clickListener = {
                    startEvent(GetWorkoutRecordsByDate(it))
                }
            }
            workoutNotes.adapter = noteAdapter.apply {
                onEditButtonAction = {
                    startEvent(NavigateToDetailsWorkout(it.id))
                }
            }
        }

        actionModeCallback = object : RemoveActionBarCallback() {
            override fun selectAllButtonHandler() {
                startEvent(ScreenEvent(HomeEvents.SelectAllWorkouts))
            }
            override fun removeButtonHandler() {
                startEvent(ScreenEvent(HomeEvents.DeleteSelectedWorkouts))
            }
            override fun onDestroyHandler() {
                startEvent(ScreenEvent(HomeEvents.HideEditState))
            }
        }
        selectHandler = object : SelectItemsHandler<WorkoutWithExercisesAndSets>(noteAdapter) {
            override fun selectedItemAction(item: WorkoutWithExercisesAndSets) {
                startEvent(RemoveWorkout(item.workout.id))
            }
        }
    }

    override fun observeData() {
        viewModel.actionBarLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                HomeEvents.DeleteSelectedWorkouts -> deleteSelectedWorkouts()
                HomeEvents.HideEditState -> hideEditState()
                HomeEvents.SelectAllWorkouts -> selectAllWorkouts()
                HomeEvents.ShowEditState -> showEditState()
                HomeEvents.StartNewWorkout -> startEvent(NavigateToWorkout)
                HomeEvents.TurnNextMonth -> turnToNextMonth()
                HomeEvents.TurnPrevMonth -> turnToPrevMonth()
            }
        }
        observeLiveData(
            viewModel.workoutRecordsLiveData,
            onSuccess = { showWorkouts(it) },
            onEmpty = { hideWorkouts() }
        )
        observeLiveData(
            viewModel.calendarLiveData,
            onSuccess = { showCalendar(it) }
        )
        observeLiveData(
            viewModel.monthAndYearLiveData,
            onSuccess = { showCurrentMonthAndYear(it) }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit -> {
                startEvent(ScreenEvent(HomeEvents.ShowEditState))
                true
            }
            else -> { false }
        }
    }

    override fun showLoading() {
        binding.state.showLoadingState()
    }

    override fun showSuccess() {
        binding.state.showSuccessState()
    }

    override fun showError(e: Exception) {
        e.printStackTrace()
    }

    override fun showEmpty() {
        binding.state.showEmptyState()
    }

    override fun showWorkouts(items: List<WorkoutWithExercisesAndSets>) {
        noteAdapter.items = items
    }

    override fun hideWorkouts() {
        noteAdapter.items = listOf()
    }

    override fun showCalendar(items: List<CalendarItemViewData>) {
        calendarAdapter.items = items.toMutableList()
        recyclerScrollToPos(items, localDateTime)
    }

    override fun showCurrentMonthAndYear(month: String) {
        binding.currentMonth.text = month
    }

    override fun showEditState() {
        noteAdapter.turnOnEditState()
        actionMode = requireActivity().startActionMode(actionModeCallback)
    }

    override fun hideEditState() {
        noteAdapter.turnOffEditState()
        actionMode?.finish()
    }

    private fun turnToPrevMonth() {
        localDateTime = localDateTime.minusMonths(1)
        setUpCalendar(localDateTime)
    }

    private fun turnToNextMonth() {
        localDateTime = localDateTime.plusMonths(1)
        setUpCalendar(localDateTime)
    }

    private fun selectAllWorkouts() {
        noteAdapter.toggleSelectAll()
    }

    private fun deleteSelectedWorkouts() {
        selectHandler?.onEachSelectedItemsAction()
        actionMode?.finish()
    }

    private fun startNewWorkout() {
        startEvent(ScreenEvent(HomeEvents.StartNewWorkout))
    }

    private fun startEvent(eventType: HomeIntent) {
        viewModel.onTriggerEvent(eventType)
    }

    private fun <T> observeLiveData(
        liveData: LiveData<DataState<T>>,
        onEmpty: () -> Unit = {},
        onLoading: () -> Unit = {},
        onError: (Exception) -> Unit = { it.printStackTrace() },
        onSuccess: (T) -> Unit = {}
    ) {
        liveData.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                DataState.Empty -> {
                    onEmpty()
                    showEmpty()
                }
                is DataState.Error -> {
                    onError(dataState.exception)
                    showError(dataState.exception)
                }
                DataState.Loading -> {
                    onLoading()
                    showLoading()
                }
                is DataState.Success -> {
                    onSuccess(dataState.data)
                    showSuccess()
                }
            }
        }
    }

    private fun recyclerScrollToPos(list: List<CalendarItemViewData>, date: LocalDateTime) {
        val pos = list.indexOfFirst { it.date.isEqual(date) }
        binding.calendar.scrollToPosition(pos - 1)
    }

    private fun setUpCalendar(date: LocalDateTime) {
        startEvent(GetMonthAndYearByDate(date))
        startEvent(GetCalendarDates(date))
    }
}
