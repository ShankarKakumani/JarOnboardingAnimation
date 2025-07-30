package com.shankarkakumani.common.util

import android.graphics.Color

object ColorParser {

    fun parseColor(colorString: String): Int {
        return try {
            when {
                colorString.startsWith("#") -> Color.parseColor(colorString)
                else -> Color.BLACK
            }
        } catch (e: IllegalArgumentException) {
            Color.BLACK
        }
    }

    fun isValidColor(colorString: String): Boolean {
        return try {
            parseColor(colorString)
            true
        } catch (e: Exception) {
            false
        }
    }
}
