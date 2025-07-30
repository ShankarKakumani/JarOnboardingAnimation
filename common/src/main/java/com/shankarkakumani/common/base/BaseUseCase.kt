package com.shankarkakumani.common.base

import com.shankarkakumani.common.result.Result

abstract class BaseUseCase<in Input, out Output> {

    suspend operator fun invoke(input: Input): Result<Output> {
        return try {
            val output = execute(input)
            Result.Success(output)
        } catch (e: Exception) {
            Result.Error(exception = e, message = e.message ?: "An unexpected error occurred")
        }
    }

    protected abstract suspend fun execute(input: Input): Output
}
