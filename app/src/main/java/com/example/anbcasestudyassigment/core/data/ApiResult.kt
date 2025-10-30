package com.example.anbcasestudyassigment.core.data

sealed class ApiResult<out T> {
    data class SuccessResult<T>(val data: T) : ApiResult<T>()
    data class ErrorResult(val error: String) : ApiResult<Nothing>()
}