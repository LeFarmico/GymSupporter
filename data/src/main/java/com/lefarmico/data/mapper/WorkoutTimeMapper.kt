package com.lefarmico.data.mapper

import com.lefarmico.data.db.entity.WorkoutTimeData
import com.lefarmico.domain.entity.WorkoutTimeDto

fun WorkoutTimeData.toDto() = WorkoutTimeDto(localTime)
