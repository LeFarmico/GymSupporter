package com.lefarmico.donetime.utils

import com.lefarmico.donetime.data.db.entities.LibraryCategory
import com.lefarmico.donetime.data.db.entities.LibraryExercise
import com.lefarmico.donetime.data.db.entities.LibrarySubCategory
import com.lefarmico.donetime.data.entities.library.ItemLibraryCategory
import com.lefarmico.donetime.data.entities.library.ItemLibraryExercise
import com.lefarmico.donetime.data.entities.library.ItemLibrarySubCategory

object Converter {

    fun convertLibraryCategoryToItemCategory(categoryDao: LibraryCategory): ItemLibraryCategory {
        return ItemLibraryCategory(
            title = categoryDao.categoryTitle,
            id = categoryDao.id
        )
    }

    fun convertLibrarySubCategoryToItemSubCategory(subCategory: LibrarySubCategory): ItemLibrarySubCategory {
        return ItemLibrarySubCategory(
            title = subCategory.subCategory,
            id = subCategory.id,
            categoryId = subCategory.categoryId,

        )
    }

    fun convertLibraryExerciseToItemExercise(exercise: LibraryExercise): ItemLibraryExercise {
        return ItemLibraryExercise(
            title = exercise.title,
            description = exercise.description,
            image = exercise.image,
            id = exercise.id,
            subCategoryId = exercise.subCategoryId
        )
    }

//    fun WorkoutData.convertWorkoutDataToWorkoutEntity(): IWorkoutEntity {
//        val date = Calendar.getInstance().time
//        val format = SimpleDateFormat("dd.MM.yyy", Locale.getDefault())
//        val formattedDate = format.format(date)
//
//        val exEntities = mutableListOf<IExerciseEntity>()
//        exercises.forEach {
//            val entity = IExerciseEntity(
//                name = it.name,
//                tag = it.tag,
//                sets = it.sets
//            )
//            exEntities.add(entity)
//        }
//        return IWorkoutEntity(date = formattedDate, exercises = exEntities)
//    }
}
