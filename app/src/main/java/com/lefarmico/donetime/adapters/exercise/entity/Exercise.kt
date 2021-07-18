package com.lefarmico.donetime.adapters.exercise.entity

import com.lefarmico.lerecycle.ItemType

class Exercise(
    exerciseName: String,
    tags: String = ""
) {
    val isActive = false
    val exerciseName = ExerciseName(exerciseName, tags)
    val addDelButtons = AddDelButtons(
        {
            addSet(100f, 45)
        },
        {
            delSet()
        }
    )
    
    private val sets = mutableListOf<ExerciseSet>()
    
    fun addSet(weights: Float, reps: Int) {
        sets.add(
            ExerciseSet(sets.size + 1, weights, reps)
        )
    }
    fun delSet() {
        sets.removeAt(sets.size - 1)
    }
    
    fun getItems(): MutableList<ItemType> {
        val itemList = mutableListOf<ItemType>()
        itemList.add(exerciseName)
        itemList.addAll(sets)
        itemList.add(addDelButtons)
        return itemList
    }

    fun setAddDelButtonListeners(addButtonCallback: () -> Unit, deleteButtonCallback: () -> Unit,) {}
}
