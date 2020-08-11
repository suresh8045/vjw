package com.viswakarma.jewelleryworks.roomdb.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Orders")
data class Orders (
    @PrimaryKey
    val orderId: String,
    var name:String,
    var date:Int,
    var phone:String,
    var description:String,
    var totalAmount:Int,
    var created:String,
    var updated:String
)