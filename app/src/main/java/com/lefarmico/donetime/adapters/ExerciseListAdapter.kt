package com.lefarmico.donetime.adapters

import com.lefarmico.donetime.data.entities.library.LibraryExercise
import com.lefarmico.donetime.data.entities.library.LibraryExerciseCategory
import com.lefarmico.donetime.data.entities.library.LibraryExerciseSubCategory
import com.lefarmico.lerecycle.LeRecyclerAdapter
import com.lefarmico.lerecycle.extractValues

class ExerciseListAdapter : LeRecyclerAdapter() {

    init {
        setItemTypes(extractValues<ExerciseListViewHolderFactory>())
    }

    fun setTypes(types: List<LibraryExerciseCategory>) {
        items = types.toMutableList()
    }

    fun setGroups(groups: List<LibraryExerciseSubCategory>) {
        items = groups.toMutableList()
    }

    fun setExercises(exercises: List<LibraryExercise>) {
        items = exercises.toMutableList()
    }
}
