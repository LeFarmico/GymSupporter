package com.lefarmico.donetime.data.entities.exercise

import com.lefarmico.donetime.data.models.ICurrentExerciseItem
import com.lefarmico.donetime.data.models.ICurrentExerciseSetItem

class ExerciseData(
    override var name: String,
    override var tag: String
) : IExerciseData {

    private var exerciseName: ExerciseNameEntity = ExerciseNameEntity(name, tag)
    override var sets: MutableList<ICurrentExerciseSetItem> = mutableListOf(ExerciseMuscleSetEntity(0f, 0))
    private var addDelButtons = AddDelButtonsEntity(
        { addSet(addButtonEvent()) },
        {
            delSet()
            delButtonEvent(this)
        }
    )
    override lateinit var addButtonEvent: (() -> ICurrentExerciseSetItem)
    override lateinit var delButtonEvent: ((ExerciseData) -> Unit)
    override var isActive: Boolean = false

    override fun getSetCount(): Int {
        return sets.size
    }

    fun addSet(setEntity: ICurrentExerciseSetItem) {
        setEntity.setNumber = sets.size + 1
        sets.add(setEntity)
    }
    private fun delSet() {
        sets.removeAt(sets.size - 1)
    }
    
    override fun getItems(): MutableList<ICurrentExerciseItem> {
        val itemList = mutableListOf<ICurrentExerciseItem>()
        itemList.add(exerciseName)
        itemList.addAll(sets)
        if (isActive) {
            itemList.add(addDelButtons)
        }
        return itemList
    }
}
