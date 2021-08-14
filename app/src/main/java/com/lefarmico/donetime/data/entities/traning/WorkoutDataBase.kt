package com.lefarmico.donetime.data.entities.traning

import com.lefarmico.donetime.data.entities.traning.exercise.ExerciseData
import com.lefarmico.donetime.data.entities.traning.exercise.ISetEntity
import com.lefarmico.donetime.utils.IWorkoutItemObservable
import com.lefarmico.donetime.utils.ItemObserver
import com.lefarmico.lerecycle.ItemType

class WorkoutDataBase : IWorkoutItemObservable, IWorkoutData {

    constructor()
    
    constructor(exerciseRepositories: List<ExerciseData>) {
        this.exercises = exerciseRepositories.toMutableList()
    }
    
    constructor(exerciseRepositories: List<ExerciseData>, activePosition: Int) {
        this.exercises = exerciseRepositories.toMutableList()
        this.activePosition = activePosition
    }

    private var activePosition = -1
    override var exercises = mutableListOf<ExerciseData>()
    override lateinit var buttonEventAddSet: (() -> ISetEntity) // FragmentResultListener
    override var buttonEventDelSet: (ExerciseData) -> Unit = { deleteEmptySetExercise(it) }
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

    private fun getExercise(position: Int): ExerciseData {
        return exercises[position]
    }
    
    fun addExercise(exerciseRepository: ExerciseData) {
        exerciseRepository.apply {
            addButtonEvent = { buttonEventAddSet.invoke() }
            delButtonEvent = { buttonEventDelSet(it) }
        }
        exercises.add(exerciseRepository)
        notifyObservers()
    }

    private fun deleteExercise(exerciseRepository: ExerciseData) {
        exercises.remove(exerciseRepository)
        notifyObservers()
    }

    private fun getItems(): MutableList<ItemType> {
        return exercises.toMutableList()
    }

    private fun deleteEmptySetExercise(exerciseRepository: ExerciseData) {
        if (exerciseRepository.getSetCount() == 0) {
            deleteExercise(exerciseRepository)
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
