package com.lefarmico.donetime.data.entities.traning.exercise

import android.os.Parcelable
import com.lefarmico.donetime.adapters.viewHolders.factories.ExerciseViewHolderFactory
import com.lefarmico.lerecycle.IViewHolderFactory
import com.lefarmico.lerecycle.ItemType
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class ExerciseNameEntity(
    val name: String,
    val tags: String,
) : ItemType, Serializable, Parcelable {
    override val type: IViewHolderFactory<ItemType> = ExerciseViewHolderFactory.EXERCISE_TITLE
}
