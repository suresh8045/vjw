package com.viswakarma.jewelleryworks.model.util

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter


fun OffsetDateTime.getDateTime(format: String = "dd/MM/yyyy, hh:mm a"): String {
    val formatter = DateTimeFormatter.ofPattern(format)
    return format(formatter)
}