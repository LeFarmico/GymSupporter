package com.lefarmico.data.mapper

import com.lefarmico.data.db.entity.LibraryData
import com.lefarmico.domain.entity.LibraryDto

fun LibraryDto.Category.toData() = LibraryData.Category(
    id = id,
    title = title
)

fun LibraryDto.SubCategory.toData() = LibraryData.SubCategory(
    id = id,
    title = title,
    categoryId = categoryId
)

fun LibraryDto.Exercise.toData() = LibraryData.Exercise(
    id = id,
    title = title,
    description = description,
    imageRes = imageRes,
    subCategoryId = subCategoryId
)
