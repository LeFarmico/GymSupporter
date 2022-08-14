package com.lefarmico.workout.interactor

import com.lefarmico.core.base.BaseState
import com.lefarmico.core.utils.Quad
import com.lefarmico.core.utils.Quint
import com.lefarmico.domain.entity.CurrentWorkoutDto
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.repository.CurrentWorkoutRepository
import com.lefarmico.domain.repository.WorkoutRecordsRepository
import com.lefarmico.domain.repository.manager.DateManager
import com.lefarmico.domain.repository.manager.TimeScheduleManager
import com.lefarmico.domain.repository.manager.WorkoutTitleManager
import com.lefarmico.workout.WorkoutEvent
import com.lefarmico.workout.extensions.toRecordsDto
import com.lefarmico.workout.reduce
import com.lefarmico.workout.reduceWorkoutId
import io.reactivex.rxjava3.core.Single
import java.time.LocalDate
import java.time.LocalTime

class WorkoutHelper(
    private val recordsRepository: WorkoutRecordsRepository,
    private val workoutRepository: CurrentWorkoutRepository,
    private val dateManager: DateManager,
    private val timeScheduleManager: TimeScheduleManager,
    private val workoutTitleManager: WorkoutTitleManager
) {

    fun saveWorkout(
        title: String,
        date: LocalDate,
        time: LocalTime?,
        exercises: List<CurrentWorkoutDto.ExerciseWithSets>,
        workoutId: Int = 0
    ): Single<BaseState> {
        val workoutDto = WorkoutRecordsDto.Workout(id = workoutId, date = date, title = title, time = time)
        val workAndExDto = WorkoutRecordsDto.WorkoutWithExercisesAndSets(workoutDto, exercises.toRecordsDto())

        return recordsRepository.addWorkoutWithExAndSets(workAndExDto)
            .map { dataState -> dataState.reduceWorkoutId() }
    }

    fun finishWorkout(): Single<Quint<
            LocalDate, LocalTime, String, List<CurrentWorkoutDto.ExerciseWithSets>, Boolean>> {
        return Single.zip(
            dateManager.getSelectedDate(),
            timeScheduleManager.getTime(),
            workoutTitleManager.getTitle(),
            workoutRepository.getExercisesWithSets(),
            workoutRepository.isUpdateMode()
        ) { date, time, title, exercises, isUpdate -> Quint(title, date, time, exercises, isUpdate).reduce() }
    }
}
