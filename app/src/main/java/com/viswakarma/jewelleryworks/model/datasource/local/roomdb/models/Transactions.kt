package com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "transactions")
data class Transactions(
    @PrimaryKey
    val id: String,
    val customerId: String,
    val orderId: Int,
    var dateTime: OffsetDateTime,
    var description: String,
    var paidAmount: Double = 0.0,
    var paidGold: Double = 0.0,
    var paidSilver: Double = 0.0
)
