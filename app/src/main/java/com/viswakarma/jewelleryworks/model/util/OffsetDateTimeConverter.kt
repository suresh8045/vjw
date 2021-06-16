package com.viswakarma.jewelleryworks.model.util

import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class OffsetDateTimeConverter {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    @TypeConverter
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return OffsetDateTime.parse(value)
    }

    @TypeConverter
    fun fromOffsetDateTime(dateTime: OffsetDateTime?): String? {
        return dateTime?.format(formatter)
    }
}