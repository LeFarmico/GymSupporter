package com.lefarmico.donetime.ui.workout.data

import com.lefarmico.lerecycle.ItemType

class Workout {

    constructor()
    
    constructor(exercises: List<Exercise>) {
        this.exercises = exercises.toMutableList()
    }
    
    constructor(exercises: List<Exercise>, activePosition: Int) {
        this.exercises = exercises.toMutableList()
        this.activePosition = activePosition
    }

    private var exercises = mutableListOf<Exercise>()
    private var activePosition = -1

    fun getExercise(position: Int): Exercise {
        return exercises[position]
    }

    fun getExercisesSize(): Int {
        return exercises.size
    }
    
    fun addExercise(exercise: Exercise) {
        exercises.add(exercise)
    }
    
    fun deleteExercise(index: Int) {
        exercises.removeAt(index)
    }
    
    fun deleteExercise(exercise: Exercise) {
        exercises.remove(exercise)
    }

    fun setActivePosition(position: Int) {
        activePosition = position
    }

    fun isActivePosition(position: Int): Boolean {
        return position == activePosition
    }

    fun getItems(): MutableList<ItemType> {
        val itemList = mutableListOf<ItemType>()
        itemList.addAll(exercises)
        return itemList
    }
}
