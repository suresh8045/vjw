package com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "customers")
data class Customer(
    @PrimaryKey
    val id: String,
    var name: String = "",
    var phone: String = "",
    var dateTime: OffsetDateTime,
    var location: String = ""
) {
    fun areItemsTheSame(customer: Customer): Boolean {
        return id == customer.id
    }

    fun areContentsTheSame(customer: Customer): Boolean {
        return this == customer
    }

    companion object{
        const val MAX_LENGTH_NAME = 32
    }
}
