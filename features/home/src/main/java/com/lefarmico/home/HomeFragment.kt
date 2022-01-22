package com.lefarmico.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearSnapHelper
import com.lefarmico.core.adapter.CalendarAdapter
import com.lefarmico.core.adapter.WorkoutRecordsAdapter
import com.lefarmico.core.base.BaseFragment
import com.lefarmico.core.decorator.ItemSpaceDecoration
import com.lefarmico.core.entity.CalendarItemViewData
import com.lefarmico.core.entity.WorkoutRecordsViewData.WorkoutWithExercisesAndSets
import com.lefarmico.core.selector.SelectItemsHandler
import com.lefarmico.core.toolbar.EditStateActionBarCallback
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.home.HomeIntent.*
import com.lefarmico.home.HomeIntent.ChangeMonth.Change.*
import com.lefarmico.home.HomeIntent.EditState.Action.*
import com.lefarmico.home.databinding.FragmentHomeBinding
import com.lefarmico.workout_notification.WorkoutRemindNotificationHelper
import java.time.LocalDate

class HomeFragment :
    BaseFragment<HomeIntent, HomeState, HomeEvent,
        FragmentHomeBinding,
        HomeViewModel>(
        FragmentHomeBinding::inflate,
        HomeViewModel::class.java
    ) {
    private val params: WorkoutRecordsDto.Workout? by lazy {
        arguments?.getParcelable(WorkoutRemindNotificationHelper.DATA_KEY)
    }
    private var actionMode: ActionMode? = null
    private var selectHandler: SelectItemsHandler<WorkoutWithExercisesAndSets>? = null
    private var actionModeCallback: EditStateActionBarCallback? = null

    private val noteAdapter = WorkoutRecordsAdapter()
    private lateinit var calendarAdapter: CalendarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        if (params != null) {
            dispatchIntent(NavigateToDetailsWorkoutScreen(params!!.id))
            arguments?.clear()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit -> {
                dispatchIntent(EditState(Show))
                true
            }
            R.id.today -> {
                dispatchIntent(BackToCurrentDate)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setUpViews() {
        dispatchIntent(ChangeMonth(Current))
        dispatchIntent(GetWorkoutRecordsByCurrentDate)
        dispatchIntent(GetDaysInMonth)
        setUpToolbar()

        binding.apply {
            prevMonthButton.setOnClickListener { dispatchIntent(ChangeMonth(Prev)) }
            nextMonthButton.setOnClickListener { dispatchIntent(ChangeMonth(Next)) }
            newWorkoutButton.setOnClickListener { dispatchIntent(NavigateToWorkoutScreen) }

            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(calendar)

            val decoratorHr = ItemSpaceDecoration(1, ItemSpaceDecoration.HORIZONTAL)
            val decoratorVert = ItemSpaceDecoration(4, ItemSpaceDecoration.VERTICAL)
            bindAdapter(LocalDate.now())
            calendar.addItemDecoration(decoratorHr, 0)
            workoutNotes.addItemDecoration(decoratorVert, 0)
            workoutNotes.adapter = noteAdapter.apply {
                onEditButtonAction = { workoutRecordsVD ->
                    dispatchIntent(NavigateToDetailsWorkoutScreen(workoutRecordsVD.id))
                }
            }
        }

        actionModeCallback = object : EditStateActionBarCallback() {
            override fun selectAllButtonHandler() { dispatchIntent(EditState(SelectAll)) }
            override fun removeButtonHandler() { dispatchIntent(EditState(DeleteSelected)) }
            override fun onDestroyHandler() { dispatchIntent(EditState(Hide)) }
        }
        selectHandler = object : SelectItemsHandler<WorkoutWithExercisesAndSets>(noteAdapter) {
            override fun selectedItemAction(item: WorkoutWithExercisesAndSets) {
                dispatchIntent(DeleteWorkout(item.workout.id))
            }
        }
    }

    private fun setUpToolbar() {
        requireActivity().title = getString(R.string.home_screen)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
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

    private fun showCalendar(items: List<CalendarItemViewData>, selectedDate: LocalDate) {
        bindAdapter(selectedDate)
        binding.calendar.adapter = calendarAdapter
        calendarAdapter.items = items.toMutableList()
        recyclerScrollToPos(items, selectedDate)
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

    private fun bindAdapter(clickedDate: LocalDate) {
        calendarAdapter = CalendarAdapter(clickedDate).apply {
            clickListener = { localDate ->
                dispatchIntent(ClickDate(localDate))
            }
        }
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
            is HomeState.CalendarResult -> showCalendar(state.calendarItemList, state.selectedDate)
            is HomeState.ExceptionResult -> throw (state.exception)
            HomeState.Loading -> showLoading()
            is HomeState.MonthAndYearResult -> showCurrentMonthAndYear(state.monthAndYearText)
            is HomeState.WorkoutResult -> showWorkouts(state.workoutList)
        }
    }
}
