package com.lefarmico.donetime.adapters.exercise.entity

import com.lefarmico.lerecycle.ItemType

class Exercise {

    private val sets: MutableList<ExerciseSet> = mutableListOf()
    var isActive: Boolean = false
    private lateinit var exerciseName: ExerciseName
    private var addDelButtons = AddDelButtons(
        {
            addButtonEvent?.let { it() }
        },
        {
            delButtonEvent?.let { it() }
        }
    )

    var addButtonEvent: (() -> Unit)? = null
    var delButtonEvent: (() -> Unit)? = null

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
        if (isActive) {
            itemList.add(addDelButtons)
        }
        return itemList
    }

    fun setNameAndTags(name: String, tags: String) {
        exerciseName = ExerciseName(name, tags)
    }

    fun getSetCount(): Int {
        return sets.size
    }
}
