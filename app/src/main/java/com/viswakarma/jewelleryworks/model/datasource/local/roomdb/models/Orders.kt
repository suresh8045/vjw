package com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "orders")
data class Orders(
    @PrimaryKey
    val id: String,
    val customerId: String,
    var name: String,
    var phone: String,
    var dateTime: OffsetDateTime,
    var image: String = "",
    var modelNo: String = "",
    var description: String = "",
    var metal: Int,
    var weight: Double = 0.0
) {

}

enum class Metal { GOLD, SILVER, COPPER, BRASS, ALLOY }