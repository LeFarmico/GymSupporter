package com.lefarmico.donetime.adapters

import com.lefarmico.donetime.data.entities.library.ItemLibraryCategory
import com.lefarmico.donetime.data.entities.library.ItemLibraryExercise
import com.lefarmico.donetime.data.entities.library.ItemLibrarySubCategory
import com.lefarmico.lerecycle.LeRecyclerAdapter
import com.lefarmico.lerecycle.extractValues

class ExerciseListAdapter : LeRecyclerAdapter() {

    init {
        setItemTypes(extractValues<ExerciseListViewHolderFactory>())
    }

    fun setTypes(types: List<ItemLibraryCategory>) {
        items = types.toMutableList()
    }

    fun setGroups(groups: List<ItemLibrarySubCategory>) {
        items = groups.toMutableList()
    }

    fun setExercises(exercises: List<ItemLibraryExercise>) {
        items = exercises.toMutableList()
    }
}
