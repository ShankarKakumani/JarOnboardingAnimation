package com.shankarkakumani.domain.resource.enum

enum class NetworkClientTypeEnum {
    RETROFIT,
    KTOR;
    
    fun getDisplayName(): String {
        return when (this) {
            RETROFIT -> "Retrofit Client"
            KTOR -> "Ktor Client"
        }
    }
} 