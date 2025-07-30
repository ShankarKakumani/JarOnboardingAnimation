package com.shankarkakumani.common.enum

enum class CacheStrategy {
    CACHE_FIRST,
    NETWORK_FIRST,
    CACHE_ONLY,
    NETWORK_ONLY;

    fun shouldCheckCache(): Boolean = this in listOf(CACHE_FIRST, CACHE_ONLY)

    fun shouldCallNetwork(): Boolean = this in listOf(NETWORK_FIRST, NETWORK_ONLY)
}
