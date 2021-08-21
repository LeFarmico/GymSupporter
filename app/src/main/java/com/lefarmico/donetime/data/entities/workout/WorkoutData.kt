package com.lefarmico.donetime.data.entities.workout

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.lefarmico.donetime.data.entities.exercise.IExerciseData
import com.lefarmico.donetime.data.entities.exercise.ISetEntity
import com.lefarmico.donetime.utils.ExercisesTypeConverter
import com.lefarmico.donetime.utils.IWorkoutItemObservable
import com.lefarmico.donetime.utils.ItemObserver
import com.lefarmico.donetime.utils.Utilities
import com.lefarmico.lerecycle.ItemType

@Entity(tableName = "workout")
@TypeConverters(ExercisesTypeConverter::class)
class WorkoutData : IWorkoutItemObservable, IWorkoutData {

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    val id: Int = 0
    @ColumnInfo(name = "date")
    override val date: String = Utilities.getCurrentDateInFormat()
    @ColumnInfo(name = "exercises")
    override var exercises = mutableListOf<IExerciseData>()
    override lateinit var buttonEventAddSet: (() -> ISetEntity) // FragmentResultListener
    override var buttonEventDelSet: (IExerciseData) -> Unit = { deleteEmptySetExercise(it) }
    override val listObservers: MutableList<ItemObserver> = mutableListOf()

    private var activePosition = -1

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

    private fun getItems(): MutableList<ItemType> {
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
