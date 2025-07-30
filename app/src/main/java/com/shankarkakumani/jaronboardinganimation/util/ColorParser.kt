package com.shankarkakumani.jaronboardinganimation.util

import android.graphics.Color

object ColorParser {
    fun parseColor(colorString: String): Int {
        return try {
            when {
                colorString.startsWith("#") -> {
                    if (colorString.length == 7) {
                        // Add full alpha if not provided
                        Color.parseColor(colorString)
                    } else {
                        Color.parseColor(colorString)
                    }
                }
                else -> Color.BLACK // Fallback
            }
        } catch (e: IllegalArgumentException) {
            Color.BLACK // Fallback for invalid colors
        }
    }
    
    fun parseColorOrDefault(colorString: String?, defaultColor: Int = Color.BLACK): Int {
        return if (colorString.isNullOrBlank()) {
            defaultColor
        } else {
            parseColor(colorString)
        }
    }
} 