package com.lefarmico.detailed_record_workout.view

import com.lefarmico.core.entity.WorkoutRecordsViewData

interface DetailedWorkoutRecordView {

    fun showDate(date: String)

    fun showWorkoutTitle(title: String)

    fun showExercises(items: MutableList<WorkoutRecordsViewData.ViewDataItemType>)
}
