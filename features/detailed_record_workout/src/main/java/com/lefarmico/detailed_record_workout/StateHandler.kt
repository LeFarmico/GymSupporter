package com.lefarmico.detailed_record_workout

import com.lefarmico.core.base.BaseState
import com.lefarmico.core.entity.WorkoutRecordsViewData
import com.lefarmico.core.mapper.toViewData
import com.lefarmico.domain.entity.WorkoutRecordsDto
import com.lefarmico.domain.utils.DataState
import java.time.format.DateTimeFormatter

fun DataState<WorkoutRecordsDto.WorkoutWithExercisesAndSets>.reduce(
    dateFormatter: DateTimeFormatter,
    timeFormatter: DateTimeFormatter
): BaseState {
    return when (this) {
        is DataState.Error -> DetailedEvent.ExceptionResult(exception)
        DataState.Loading -> DetailedEvent.Loading
        is DataState.Success -> {
            val dto = this.data
            val workout = dto.workout.toViewData(dateFormatter, timeFormatter)
            val exercises = mutableListOf<WorkoutRecordsViewData.ViewDataItemType>()
            // TODO : Избавиться от логики
            val exerciseListViewData = dto.exerciseWithSetsList.toViewData()
            for (i in exerciseListViewData.indices) {
                exercises.add(exerciseListViewData[i].exercise)
                exercises.addAll(exerciseListViewData[i].setList)
            }
            DetailedState.WorkoutResult(workout, exercises)
        }
    }
}
