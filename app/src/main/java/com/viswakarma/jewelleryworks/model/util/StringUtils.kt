package com.viswakarma.jewelleryworks.model.util

import androidx.core.text.isDigitsOnly
import java.util.regex.Pattern

fun String.isValid(
    canBeBlank: Boolean = false,
    isDigitsOnly: Boolean = false,
    minLength: Int = 0,
    maxlength: Int = 20,
    pattern: Pattern? = null
): Boolean {
    return this.run {
        if (canBeBlank) {
            true
        } else {
            isNotBlank()
        } && if (isDigitsOnly) {
            this.isDigitsOnly()
        } else {
            true
        } && length >= minLength && length <= maxlength && pattern?.matcher(this)
            ?.matches() ?: true
    }
}