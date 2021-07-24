package com.lefarmico.donetime.ui.workout.data

import com.lefarmico.donetime.ui.workout.adapters.workout.WorkoutViewHolderFactory
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType

class Exercise : ItemType {

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

    override val type: IViewHolderFactory<ItemType> = WorkoutViewHolderFactory.EXERCISE
}
