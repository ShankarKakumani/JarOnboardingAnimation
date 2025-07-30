package com.shankarkakumani.common.util

object DateUtils {

    fun getCurrentTimestamp(): Long = System.currentTimeMillis()

    fun isExpired(timestamp: Long, validityMillis: Long): Boolean {
        return getCurrentTimestamp() - timestamp > validityMillis
    }
}
