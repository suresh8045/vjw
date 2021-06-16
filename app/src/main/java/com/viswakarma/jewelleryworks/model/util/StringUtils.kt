package com.viswakarma.jewelleryworks.model.util

import java.util.regex.Pattern

fun String.isValid(
    canBeBlank: Boolean = false,
    minLength: Int = 0,
    maxlength: Int = 20,
    pattern: Pattern? = null
): Boolean {
    return this.run {
        if (canBeBlank) {
            true
        } else {
            isNotBlank()
        } && length >= minLength && length <= maxlength && pattern?.matcher(this)
            ?.matches() ?: true
    }
}