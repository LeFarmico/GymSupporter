package com.lefarmico.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.core.adapter.diffUtil.WorkoutRecordsDiffCallback
import com.lefarmico.core.databinding.ItemNoteWorkoutBinding
import com.lefarmico.core.entity.WorkoutRecordsViewData

class WorkoutRecordsAdapter : RecyclerView.Adapter<WorkoutRecordsAdapter.WorkoutNoteViewHolder>() {

    lateinit var editButtonCallback: (WorkoutRecordsViewData.Workout) -> Unit

    var items = listOf<WorkoutRecordsViewData.WorkoutWithExercisesAndSets>()
        set(value) {
            val oldField = field
            field = value
            val diffCallback = WorkoutRecordsDiffCallback(oldField, field)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
        }

    class WorkoutNoteViewHolder(
        itemNoteWorkoutBinding: ItemNoteWorkoutBinding
    ) : RecyclerView.ViewHolder(itemNoteWorkoutBinding.root) {

        private val date = itemNoteWorkoutBinding.workoutDate
        private val exerciseNoteRecycler = itemNoteWorkoutBinding.exercisesRecycler

        val editButton = itemNoteWorkoutBinding.edit

        fun bind(noteWorkout: WorkoutRecordsViewData.Workout) {
            date.text = noteWorkout.date
        }
        fun bindAdapter(adapter: ExerciseRecordsAdapter) {
            exerciseNoteRecycler.adapter = adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutNoteViewHolder {
        return WorkoutNoteViewHolder(
            ItemNoteWorkoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WorkoutNoteViewHolder, position: Int) {
        val adapter = ExerciseRecordsAdapter()
        adapter.setExercise(items[position].exerciseWithSetsList)
        holder.bind(items[position].workout)
        holder.bindAdapter(adapter)

        holder.editButton.setOnClickListener {
            editButtonCallback(items[position].workout)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
