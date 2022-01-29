package com.lefarmico.data.loader

import com.lefarmico.data.dataStore.MuscleCategoryDataStore
import com.lefarmico.data.db.dao.LibraryDao
import com.lefarmico.data.db.entity.LibraryData
import com.lefarmico.data.extensions.dataStateResolver
import com.lefarmico.data.mapper.toData
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.loaders.MuscleCategoryLoader
import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MuscleCategoryLoaderImpl @Inject constructor(
    private val dataStore: MuscleCategoryDataStore,
    private val libraryDao: LibraryDao
) : MuscleCategoryLoader {

    // TODO обработать ошибки
    override fun loadMuscleCategory(): Single<DataState<Boolean>> {
        val muscleCat = dataStore.getMuscleCategory()
        return libraryDao.getCategories()
            .map { categories ->
                val isExisted = categories.any { it.title == muscleCat.title }
                if (!isExisted) {
                    val catData = LibraryDto.Category(title = muscleCat.title).toData()
                    libraryDao.insertCategory(catData)
                        .doAfterSuccess { catId ->
                            val subCatData = muscleCat.subCategoryList.map { muscleData ->
                                LibraryData.SubCategory(
                                    title = muscleData.title,
                                    categoryId = catId.toInt()
                                )
                            }
                            libraryDao.insertSubCategories(subCatData).subscribe()
                        }.subscribe()
                }
                dataStateResolver { true }
            }
    }
}
