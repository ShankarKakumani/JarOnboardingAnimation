package com.shankarkakumani.jaronboardinganimation.util

import android.graphics.Color
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.core.graphics.toColorInt

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
    
    /**
     * Parse hex color string to Compose Color
     */
    fun parseHexColor(hexString: String): ComposeColor {
        return try {
            val colorString = if (hexString.startsWith("#")) hexString else "#$hexString"
            ComposeColor(colorString.toColorInt())
        } catch (e: Exception) {
            ComposeColor.Gray // Fallback color
        }
    }
    
    /**
     * Parse hex color string to Compose Color with nullable input
     */
    fun parseHexColorOrDefault(hexString: String?, defaultColor: ComposeColor = ComposeColor.Gray): ComposeColor {
        return if (hexString.isNullOrBlank()) {
            defaultColor
        } else {
            parseHexColor(hexString)
        }
    }
} 