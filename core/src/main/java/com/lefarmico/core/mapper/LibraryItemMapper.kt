package com.lefarmico.core.mapper

import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.entity.MuscleDto

/**
 *  Converts the [MuscleDto.Category] to the [LibraryViewData.Category]
 *   as a new instance.
 *
 *  Used as ViewData entity
 */
fun LibraryDto.Category.toViewData() = LibraryViewData.Category(
    id = id,
    title = title
)

/**
 *  Converts the [MuscleDto.SubCategory] to the [LibraryViewData.SubCategory]
 *   as a new instance.
 *
 *  Used as ViewData entity
 */
fun LibraryDto.SubCategory.toViewData() = LibraryViewData.SubCategory(
    id = id,
    title = title,
    categoryId = categoryId
)

/**
 *  Converts the [MuscleDto.Exercise] to the [LibraryViewData.Exercise]
 *   as a new instance.
 *
 *  Used as ViewData entity
 */
fun LibraryDto.Exercise.toViewData() = LibraryViewData.Exercise(
    id = id,
    title = title,
    description = description,
    imageRes = imageRes,
    subCategoryId = subCategoryId
)
/**  Converts a [List] of the [MuscleDto.Exercise] to a [List] of the [LibraryViewData.Exercise]
 *   as a new instance.
 *
 *  Used as ViewData entity
 */
@JvmName("toLibraryViewDataExercise")
fun List<LibraryDto.Exercise>.toViewData() = this.map { it.toViewData() }
