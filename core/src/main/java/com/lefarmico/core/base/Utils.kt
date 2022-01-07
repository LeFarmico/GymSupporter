package com.lefarmico.core.base

import android.view.LayoutInflater
import android.view.ViewGroup

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
typealias LayoutInflate<T> = (LayoutInflater) -> T
