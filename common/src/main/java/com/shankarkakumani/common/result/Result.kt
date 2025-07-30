package com.shankarkakumani.common.result

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable, val message: String? = null) : Result<Nothing>()
    data class Loading(val isLoading: Boolean = true) : Result<Nothing>()

    val isSuccess: Boolean
        get() = this is Success
    val isError: Boolean
        get() = this is Error

    fun getOrNull(): T? =
            when (this) {
                is Success -> data
                else -> null
            }

    fun getOrThrow(): T =
            when (this) {
                is Success -> data
                is Error -> throw exception
                is Loading -> throw IllegalStateException("Result is still loading")
            }
}

inline fun <T> Result<T>.onSuccess(action: (value: T) -> Unit): Result<T> {
    if (this is Result.Success) action(data)
    return this
}

inline fun <T> Result<T>.onError(action: (exception: Throwable) -> Unit): Result<T> {
    if (this is Result.Error) action(exception)
    return this
}

fun <T> Result<T>.fold(
        onSuccess: (T) -> Unit = {},
        onError: (Throwable) -> Unit = {},
        onLoading: (Boolean) -> Unit = {}
) {
    when (this) {
        is Result.Success -> onSuccess(data)
        is Result.Error -> onError(exception)
        is Result.Loading -> onLoading(isLoading)
    }
}
