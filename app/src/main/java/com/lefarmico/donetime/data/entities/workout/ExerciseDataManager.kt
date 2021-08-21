package com.lefarmico.donetime.data.entities.workout

import com.lefarmico.donetime.data.entities.exercise.IExerciseData
import com.lefarmico.donetime.data.models.ICurrentExerciseItem
import com.lefarmico.donetime.data.models.ICurrentExerciseSetItem
import com.lefarmico.donetime.utils.ItemObservable
import com.lefarmico.donetime.utils.ItemObserver
import com.lefarmico.donetime.utils.Utilities

class ExerciseDataManager : ItemObservable<ICurrentExerciseItem>, IWorkoutData {

    val id: Int = 0
    override val date: String = Utilities.getCurrentDateInFormat()
    override var exercises = mutableListOf<IExerciseData>()
    override lateinit var buttonEventAddSet: (() -> ICurrentExerciseSetItem) // FragmentResultListener
    override var buttonEventDelSet: (IExerciseData) -> Unit = { deleteEmptySetExercise(it) }
    override val listObservers: MutableList<ItemObserver<ICurrentExerciseItem>> = mutableListOf()

    private var activePosition = -1

    fun setActiveExercise(position: Int) {
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

    private fun getItems(): MutableList<ICurrentExerciseItem> {
        val itemsList = mutableListOf<ICurrentExerciseItem>()
        exercises.forEach { 
            val exItems = it.getItems()
            itemsList.addAll(exItems)
        }
        return itemsList
    }

    private fun deleteEmptySetExercise(exerciseData: IExerciseData) {
        if (exerciseData.getSetCount() == 0) {
            deleteExercise(exerciseData)
        }
    }

    override fun registerObserver(observer: ItemObserver<ICurrentExerciseItem>) {
        observer.updateData(getItems())
        listObservers.add(observer)
    }

    override fun removeObserver(observer: ItemObserver<ICurrentExerciseItem>) {
        listObservers.remove(observer)
    }

    override fun notifyObservers() {
        listObservers.forEach { 
            it.updateData(getItems())
        }
    }
}
