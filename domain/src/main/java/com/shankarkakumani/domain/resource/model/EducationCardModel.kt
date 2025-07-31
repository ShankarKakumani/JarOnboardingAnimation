package com.shankarkakumani.domain.resource.model

import java.util.UUID

data class EducationCardModel(
    val id: String = UUID.randomUUID().toString(),
    val image: String,
    val endGradient: String,
    val startGradient: String,
    val strokeEndColor: String,
    val startY: Float,
    val backgroundColor: String,
    val expandStateText: String,
    val strokeStartColor: String,
    val collapsedStateText: String
) {
    fun isValid(): Boolean {
        return image.isNotBlank() &&
               expandStateText.isNotBlank() &&
               collapsedStateText.isNotBlank() &&
               isValidColor(backgroundColor) &&
               isValidColor(strokeStartColor) &&
               isValidColor(strokeEndColor)
    }
    
    fun hasGradient(): Boolean {
        return startGradient.isNotBlank() && endGradient.isNotBlank()
    }
    
    private fun isValidColor(color: String): Boolean {
        return color.matches(Regex("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})$"))
    }
} 