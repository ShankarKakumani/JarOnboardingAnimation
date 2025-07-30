package com.shankarkakumani.domain.resource.exception

open class OnboardingDomainException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)

class InvalidAnimationConfigException(
    message: String,
    cause: Throwable? = null
) : OnboardingDomainException(message, cause)

class DataValidationException(
    message: String,
    val field: String? = null,
    cause: Throwable? = null
) : OnboardingDomainException(message, cause)

class OnboardingDataNotFoundException(
    message: String = "Onboarding data not found",
    cause: Throwable? = null
) : OnboardingDomainException(message, cause)

class NetworkUnavailableException(
    message: String = "Network is unavailable",
    cause: Throwable? = null
) : OnboardingDomainException(message, cause)

class CacheExpiredException(
    message: String = "Cached data has expired",
    cause: Throwable? = null
) : OnboardingDomainException(message, cause) 