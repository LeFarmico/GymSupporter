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
import com.lefarmico.core.toolbar.RemoveActionBarCallback
import com.lefarmico.core.toolbar.RemoveActionBarEvents.*
import com.lefarmico.domain.utils.DataState
import com.lefarmico.home.R
import com.lefarmico.home.databinding.FragmentHomeBinding
import com.lefarmico.home.intent.HomeIntent
import com.lefarmico.home.intent.HomeIntent.*
import com.lefarmico.home.viewModel.HomeViewModel
import java.lang.Exception
import java.time.LocalDate
import java.time.LocalDateTime

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate,
    HomeViewModel::class.java
) {

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
            prevMonthButton.setOnClickListener {
                localDateTime = localDateTime.minusMonths(1)
                setUpCalendar(localDateTime)
            }
            nextMonthButton.setOnClickListener {
                localDateTime = localDateTime.plusMonths(1)
                setUpCalendar(localDateTime)
            }

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
            newWorkoutButton.setOnClickListener {
                startEvent(NavigateToWorkout)
            }
        }

        actionModeCallback = object : RemoveActionBarCallback() {
            override fun selectAllButtonHandler() {
                startEvent(ActionBarEvent(SelectAll))
            }

            override fun removeButtonHandler() {
                startEvent(ActionBarEvent(DeleteItems))
            }

            override fun onDestroyHandler() {
                startEvent(ActionBarEvent(Close))
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
                SelectAll -> noteAdapter.toggleSelectAll()
                DeleteItems -> {
                    selectHandler?.onEachSelectedItemsAction()
                    actionMode?.finish()
                }
                Launch -> {
                    noteAdapter.turnOnEditState()
                    actionMode = requireActivity().startActionMode(actionModeCallback)
                }
                Close -> {
                    noteAdapter.turnOffEditState()
                    actionMode?.finish()
                }
            }
        }
        observeLiveData(
            viewModel.workoutRecordsLiveData,
            onSuccess = { noteAdapter.items = it },
            onEmpty = { noteAdapter.items = listOf() }
        )
        observeLiveData(
            viewModel.calendarLiveData,
            onSuccess = {
                calendarAdapter.items = it.toMutableList()
                recyclerScrollToPos(it, localDateTime)
            }
        )
        observeLiveData(
            viewModel.monthAndYearLiveData,
            onSuccess = { binding.currentMonth.text = it }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit -> {
                startEvent(ActionBarEvent(Launch))
                true
            }
            else -> { false }
        }
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
                }
                is DataState.Error -> {
                    onError(dataState.exception)
                }
                DataState.Loading -> {
                    onLoading()
                    binding.state.showLoadingState()
                }
                is DataState.Success -> {
                    onSuccess(dataState.data)
                    binding.state.showSuccessState()
                }
            }
        }
    }

    private fun recyclerScrollToPos(list: List<CalendarItemViewData>, date: LocalDateTime) {
        binding.calendar.scrollToPosition(
            list.indexOfFirst {
                it.date.isEqual(date)
            } - 1
        )
    }

    private fun setUpCalendar(date: LocalDateTime) {
        startEvent(GetMonthAndYearByDate(date))
        startEvent(GetCalendarDates(date))
    }
}
