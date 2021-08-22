package com.lefarmico.donetime.data.entities.currentExercise

import com.lefarmico.donetime.data.models.ICurrentExerciseItem

class ExerciseData private constructor(
    override var name: String,
    override var tag: String
) : IExerciseModel, ICurrentExerciseItem {

    class Builder {
        private lateinit var name: String
        private lateinit var tag: String
        private lateinit var addButtonCallback: ((ExerciseData) -> Unit)
        private lateinit var delButtonCallback: ((ExerciseData) -> Unit)

        fun setName(name: String): Builder {
            this.name = name
            return this
        }

        fun setTag(tag: String): Builder {
            this.tag = tag
            return this
        }
        fun setAddButtonCallback(callback: (ExerciseData) -> Unit): Builder {
            addButtonCallback = callback
            return this
        }

        fun setDelButtonCallback(callback: (ExerciseData) -> Unit): Builder {
            delButtonCallback = callback
            return this
        }

        fun build(): ExerciseData {
            return ExerciseData(name, tag).apply {
                this.addButtonEvent = addButtonCallback
                this.delButtonEvent = delButtonCallback
            }
        }
    }

    override var exerciseSetList: ExerciseSetList = ExerciseSetList(mutableListOf(ExerciseSetEntity(0f, 0)))
    private var addDelButtons = AddDelButtons(
        {
            addButtonEvent(this)
        },
        {
            delSet()
            delButtonEvent(this)
        }
    )

    override lateinit var addButtonEvent: ((ExerciseData) -> Unit)
    override lateinit var delButtonEvent: ((ExerciseData) -> Unit)
    override var isActive: Boolean = true

    override fun getSetCount(): Int {
        return exerciseSetList.setList.size
    }

    fun addSet(setEntity: ExerciseSetEntity) {
        setEntity.setNumber = exerciseSetList.setList.size + 1
        exerciseSetList.setList.add(setEntity)
    }
    private fun delSet() {
        exerciseSetList.setList.removeAt(exerciseSetList.setList.size - 1)
    }
    
    override fun getItems(): MutableList<ICurrentExerciseItem> {
        val itemList = mutableListOf<ICurrentExerciseItem>()
        itemList.add(this)
//        itemList.add(exerciseName)
//        itemList.add(exerciseSets)
        if (isActive) {
            itemList.add(addDelButtons)
        }
        return itemList
    }
}
