package com.shankarkakumani.common.exception

open class CommonException(message: String, cause: Throwable? = null) : Exception(message, cause)

class ValidationException(message: String, val field: String? = null, cause: Throwable? = null) :
        CommonException(message, cause)

class NetworkException(message: String, val code: Int? = null, cause: Throwable? = null) :
        CommonException(message, cause)
