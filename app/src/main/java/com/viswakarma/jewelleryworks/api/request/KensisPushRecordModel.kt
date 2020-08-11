package com.viswakarma.jewelleryworks.api.request

import com.google.gson.JsonObject

data class KensisPushRecordModel (
    var offSet:Int,
    var userId:String,
    var deviceId:String,
    var tables:List<JsonObject>
    )