package com.shankarkakumani.common.ext

import com.shankarkakumani.common.util.ColorParser

fun String.isValidColor(): Boolean = ColorParser.isValidColor(this)

fun String.toColorInt(): Int = ColorParser.parseColor(this)

fun String.capitalizeWords(): String {
    return split(" ").joinToString(" ") { word ->
        word.lowercase().replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        }
    }
}

fun String?.orDefault(default: String = ""): String = this ?: default
