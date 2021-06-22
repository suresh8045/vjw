package com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey
    val id: String,
    val customerId: String,
    var name: String,
    var phone: String,
    var dateTime: OffsetDateTime,
    var image: String = "",
    var modelNo: String = "",
    var description: String = "",
    var metal: String,
    var weight: Double = 0.0
) {

    fun getWeightFormat(): String {
        return "W %.3fg".format(weight)
    }

}

enum class Metal(val value:String) { GOLD("Gold"), SILVER("Silver"), COPPER("Copper"), BRASS("Brass"), ALLOY("Alloy") }