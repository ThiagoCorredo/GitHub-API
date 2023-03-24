package com.tcorredo.githubapi.data

import retrofit2.HttpException

sealed class ResultState<out T> {
    data class Success<T>(val data: T?) : ResultState<T>()
    object EmptyData : ResultState<Nothing>()
    object NoMorePage : ResultState<Nothing>()
    data class Error(val error: HttpException) : ResultState<Nothing>()
    data class ErrorFatal(val exception: Exception) : ResultState<Nothing>()
}