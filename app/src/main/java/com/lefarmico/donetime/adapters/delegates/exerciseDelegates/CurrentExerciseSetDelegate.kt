package com.lefarmico.donetime.adapters.delegates.exerciseDelegates

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.lefarmico.donetime.R
import com.lefarmico.donetime.data.entities.exercise.ExerciseMuscleSetEntity
import com.lefarmico.donetime.data.models.ICurrentExerciseItem
import com.lefarmico.donetime.databinding.ItemExerciseSetBinding

class CurrentExerciseSetDelegate : AbsListItemAdapterDelegate<
    ExerciseMuscleSetEntity,
    ICurrentExerciseItem,
    CurrentExerciseSetDelegate.SetViewHolder
    >() {

    companion object {
        const val TOP_CORNER = 0
        const val MIDDLE_CORNER = 1
        const val BOTTOM_CORNER = 2
        const val SINGLE_CORNER = 3
    }
    class SetViewHolder(
        private val exerciseItemBinding: ItemExerciseSetBinding
    ) : RecyclerView.ViewHolder(exerciseItemBinding.root) {
        private val setNumber: TextView = exerciseItemBinding.setNumber
        private val weights: TextView = exerciseItemBinding.weights
        private var reps = exerciseItemBinding.reps

        fun bind(item: ExerciseMuscleSetEntity) {
            setNumber.text = "${item.setNumber}. Set"
            weights.text = "${item.weights} Kg"
            reps.text = "${item.reps} Reps"
        }

        fun bindBackGround(@DrawableRes drawable: Int) {
            exerciseItemBinding.root.setBackgroundResource(drawable)
        }
    }

    override fun isForViewType(
        item: ICurrentExerciseItem,
        items: MutableList<ICurrentExerciseItem>,
        position: Int
    ): Boolean {
        return item is ExerciseMuscleSetEntity
    }

    override fun onCreateViewHolder(parent: ViewGroup): SetViewHolder {
        return SetViewHolder(
            ItemExerciseSetBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        item: ExerciseMuscleSetEntity,
        holder: SetViewHolder,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            when (payloads[0]) {
                TOP_CORNER -> { holder.bindBackGround(R.drawable.top_corners_shape) }
                MIDDLE_CORNER -> { holder.bindBackGround(R.drawable.middle_corners_shape) }
                BOTTOM_CORNER -> { holder.bindBackGround(R.drawable.bottom_corners_shape) }
                SINGLE_CORNER -> { holder.bindBackGround(R.drawable.single_corners_shape) }
            }
        }
        holder.bind(item)
    }
}
