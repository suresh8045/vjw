package com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey
    val id: String,
    val customerId: String,
    val orderId: String,
    var dateTime: OffsetDateTime,
    var type: String,
    var description: String = "",
    var amount: Int = 0,
    var metal: String,
    var metalState: Int = 0,
    var metalRatePerTenGrams: Int,
    var weight: Double = 0.0,
    var purity: Float = 99.9f,
    var stoneType: Int = 0,
    var stoneCount: Int = 0,
) {
    fun getWeightFormat(): String {
        return "W %.3fg".format(weight)
    }

    fun getPurityFormat(): String {
        return "(%.1f%%)".format(purity)
    }

    fun getAmountFormat(): String {
        return "$amount R"
    }
}

enum class TransactionType(val type: String) {
    MISCELLANEOUS_CHARGES("Miscellaneous Charges"), MAKING_CHARGE("Making Charge"), STONE_COST("Stone Cost"), DISCOUNT(
        "Discount"
    ),
    PAID_AMOUNT("Paid Amount"), METAL_EXTRA_ADDED("Metal Extra Added"), METAL_RECEIVED("Metal Received"), ORNAMENT_WEIGHT_FINAL(
        "Ornament Weight final"
    ),
    STONE_WEIGHT_MINUS("Stone Weight Minus"), WASTAGE("Wastage")
}

enum class MetalState(val state: Int) {
    SOLID_METAL(1), OLD_ORNAMENT(2), PIECES(3), POWDER(4)
}