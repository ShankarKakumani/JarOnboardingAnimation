package com.shankarkakumani.domain.resource.model

data class HeaderDataModel(
    val title: String,
    val iconUrl: String
) {
    fun isValid(): Boolean {
        return title.isNotBlank() && iconUrl.isNotBlank()
    }
    
    fun hasValidIcon(): Boolean {
        return iconUrl.startsWith("http") || iconUrl.startsWith("file://")
    }
} 