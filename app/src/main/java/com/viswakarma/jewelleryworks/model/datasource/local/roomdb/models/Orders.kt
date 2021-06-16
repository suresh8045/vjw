package com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "orders")
data class Orders(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int = 0,
    val customerId: String,
    var name: String,
    var phone: String,
    var dateTime: OffsetDateTime,
    var image: String = "",
    var description: String,
    var totalAmount: Int
)