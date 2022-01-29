package com.lefarmico.domain.loaders

import com.lefarmico.domain.utils.DataState
import io.reactivex.rxjava3.core.Single

interface MuscleCategoryLoader {

    fun loadMuscleCategory(): Single<DataState<Boolean>>
}
