package com.viswakarma.jewelleryworks.util

import java.text.SimpleDateFormat
import java.util.*

object Common {

    fun getDateString(seconds:Int):String{
        return SimpleDateFormat("dd MMM yyyy", Locale.ROOT).format(Date(seconds.toLong()*1000L))
    }

    fun getDateTimeStamp(seconds:Int):String{
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ROOT).format(Date(seconds.toLong()*1000L))
    }
}