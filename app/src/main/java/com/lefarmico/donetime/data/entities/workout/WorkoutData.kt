package com.lefarmico.donetime.data.entities.workout

import com.lefarmico.donetime.data.entities.workout.exercise.ExerciseData
import com.lefarmico.donetime.data.entities.workout.exercise.IExerciseData
import com.lefarmico.donetime.data.entities.workout.exercise.ISetEntity
import com.lefarmico.donetime.utils.IWorkoutItemObservable
import com.lefarmico.donetime.utils.ItemObserver
import com.lefarmico.lerecycle.ItemType

class WorkoutData : IWorkoutItemObservable, IWorkoutData {

    constructor()
    
    constructor(exerciseRepositories: List<ExerciseData>) {
        this.exercises = exerciseRepositories.toMutableList()
    }
    
    constructor(exerciseRepositories: List<ExerciseData>, activePosition: Int) {
        this.exercises = exerciseRepositories.toMutableList()
        this.activePosition = activePosition
    }

    private var activePosition = -1
    override var exercises = mutableListOf<IExerciseData>()
    override lateinit var buttonEventAddSet: (() -> ISetEntity) // FragmentResultListener
    override var buttonEventDelSet: (IExerciseData) -> Unit = { deleteEmptySetExercise(it) }
    override val listObservers: MutableList<ItemObserver> = mutableListOf()

    override fun setActivePosition(position: Int) {
        val currentActivePos = activePosition
        try {
            getExercise(currentActivePos).isActive = false
        } catch (e: IndexOutOfBoundsException) {}
        activePosition = position
        getExercise(activePosition).isActive = true
        notifyObservers()
    }

    private fun getExercise(position: Int): IExerciseData {
        return exercises[position]
    }
    
    fun addExercise(exerciseData: IExerciseData) {
        exerciseData.apply {
            addButtonEvent = { buttonEventAddSet.invoke() }
            delButtonEvent = { buttonEventDelSet(it) }
        }
        exercises.add(exerciseData)
        notifyObservers()
    }

    private fun deleteExercise(exerciseData: IExerciseData) {
        exercises.remove(exerciseData)
        notifyObservers()
    }

    fun getItems(): MutableList<ItemType> {
        return exercises.toMutableList()
    }

    private fun deleteEmptySetExercise(exerciseData: IExerciseData) {
        if (exerciseData.getSetCount() == 0) {
            deleteExercise(exerciseData)
        }
    }

    override fun registerObserver(observer: ItemObserver) {
        observer.updateData(getItems())
        listObservers.add(observer)
    }

    override fun removeObserver(observer: ItemObserver) {
        listObservers.remove(observer)
    }

    override fun notifyObservers() {
        listObservers.forEach { 
            it.updateData(getItems())
        }
    }
}
