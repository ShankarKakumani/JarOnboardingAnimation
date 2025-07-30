package com.shankarkakumani.domain.resource.model

data class SaveButtonModel(
    val text: String,
    val textColor: String,
    val strokeColor: String,
    val backgroundColor: String,
    val icon: String? = null,
    val deeplink: String? = null
) {
    fun isValid(): Boolean {
        return text.isNotBlank() &&
               isValidColor(textColor) &&
               isValidColor(strokeColor) &&
               isValidColor(backgroundColor)
    }
    
    fun hasIcon(): Boolean = !icon.isNullOrBlank()
    
    fun hasDeeplink(): Boolean = !deeplink.isNullOrBlank()
    
    private fun isValidColor(color: String): Boolean {
        return color.matches(Regex("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})$"))
    }
} 