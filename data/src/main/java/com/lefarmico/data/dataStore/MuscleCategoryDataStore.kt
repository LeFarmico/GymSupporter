package com.lefarmico.data.dataStore

import android.content.Context
import com.lefarmico.data.R
import com.lefarmico.data.db.entity.MuscleData
import com.lefarmico.data.utils.JSONResourceReader
import javax.inject.Inject

class MuscleCategoryDataStore @Inject constructor(
    private val context: Context
) {

    fun getMuscleCategory(): MuscleData.Category {
        val reader = JSONResourceReader(context.resources, R.raw.muscles_category)
        return reader.constructWithGson(MuscleData.Category::class.java)
    }
}
