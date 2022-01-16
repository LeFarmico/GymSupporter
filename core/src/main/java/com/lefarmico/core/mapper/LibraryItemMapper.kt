package com.lefarmico.core.mapper

import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.domain.entity.LibraryDto

/**
 *  Converts the [LibraryDto.Category] to the [LibraryViewData.Category]
 *   as a new instance.
 *
 *  Used as ViewData entity
 */
fun LibraryDto.Category.toViewData() = LibraryViewData.Category(
    id = id,
    title = title
)

/**
 *  Converts the [LibraryDto.SubCategory] to the [LibraryViewData.SubCategory]
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
 *  Converts the [LibraryDto.Exercise] to the [LibraryViewData.Exercise]
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

// TODO чекнуть
// /**
// *  Converts a [List] of the [LibraryViewData.Exercise] to a [List] of the [CurrentWorkoutViewData.Set]
// *   as a new instance.
// *
// *  Used as ViewData entity
// */
// fun List<LibraryViewData.Exercise>.toViewData() = this.map { it.toDto() }

/**  Converts a [List] of the [LibraryDto.Exercise] to a [List] of the [LibraryViewData.Exercise]
 *   as a new instance.
 *
 *  Used as ViewData entity
 */
@JvmName("toLibraryViewDataExercise")
fun List<LibraryDto.Exercise>.toViewData() = this.map { it.toViewData() }
