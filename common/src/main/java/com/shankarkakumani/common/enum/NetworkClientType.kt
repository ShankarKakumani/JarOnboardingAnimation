package com.shankarkakumani.common.enum

enum class NetworkClientType(val displayName: String) {
    RETROFIT("Retrofit"),
    KTOR("Ktor");

    companion object {
        fun fromString(value: String): NetworkClientType {
            return values().find { it.name.equals(value, ignoreCase = true) } ?: RETROFIT
        }
    }
}
