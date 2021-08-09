package com.lefarmico.donetime.data.entities.traning

import com.lefarmico.donetime.data.entities.traning.exercise.ExerciseDataBase
import com.lefarmico.donetime.data.entities.traning.exercise.ISetEntity
import com.lefarmico.donetime.utils.IWorkoutItemObservable
import com.lefarmico.donetime.utils.ItemObserver
import com.lefarmico.lerecycle.ItemType

class WorkoutDataBase : IWorkoutItemObservable {

    constructor()
    
    constructor(exerciseRepositories: List<ExerciseDataBase>) {
        this.exercises = exerciseRepositories.toMutableList()
    }
    
    constructor(exerciseRepositories: List<ExerciseDataBase>, activePosition: Int) {
        this.exercises = exerciseRepositories.toMutableList()
        this.activePosition = activePosition
    }

    private var exercises = mutableListOf<ExerciseDataBase>()
    private var activePosition = -1
    var addExerciseButton = AddExerciseEntity {
        if (addExButtonEvent != null) {
//            addExercise(addExButtonEvent!!.invoke())
        }
    }
    var addExButtonEvent: (() -> Unit)? = null
    var addSetButtonEvent: (() -> ISetEntity)? = null
    var deleteSetButtonEvent: (() -> Unit) = { }

    override fun setActivePosition(position: Int) {
        try {
            val currentActivePos = getActivePosition()
            getExercise(currentActivePos).isActive = false
        } catch (e: ArrayIndexOutOfBoundsException) {}
        activePosition = position
        getExercise(activePosition).isActive = true
        notifyObservers()
    }

    override val listObservers: MutableList<ItemObserver> = mutableListOf()

    fun getExercise(position: Int): ExerciseDataBase {
        return try {
            exercises[position]
        } catch (e: ArrayIndexOutOfBoundsException) {
            throw (e)
        }
    }
    
    fun addExercise(exerciseRepository: ExerciseDataBase) {
        exerciseRepository.addButtonEvent = {
            if (addSetButtonEvent != null) {
                exerciseRepository.addSet(addSetButtonEvent!!.invoke())
            }
        }
        exerciseRepository.delButtonEvent = {
            exerciseRepository.delSet()
            deleteEmptySetExercise(exerciseRepository)
        }
        exercises.add(exerciseRepository)
        notifyObservers()
    }
    
    fun deleteExercise(index: Int) {
        exercises.removeAt(index)
        notifyObservers()
    }

    fun deleteExercise(exerciseRepository: ExerciseDataBase) {
        exercises.remove(exerciseRepository)
        notifyObservers()
    }

    fun getActivePosition(): Int {
        return activePosition
    }

    fun isActivePosition(position: Int): Boolean {
        return position == activePosition
    }

    private fun getItems(): MutableList<ItemType> {
        val itemList = mutableListOf<ItemType>()
        itemList.addAll(exercises)
        itemList.add(addExerciseButton)
        return itemList
    }

    fun deleteEmptySetExercise(exerciseRepository: ExerciseDataBase) {
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
