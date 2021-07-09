package com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime
import java.util.*

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey
    val id: String,
    val customerId: String = "",
    val orderId: String = "",
    var dateTime: OffsetDateTime,
    var type: String,
    var description: String = "",
    var amount: Int = 0,
    var metal: String = Metal.UNKNOWN.value,
    var metalState: Int = 0,
    var metalRatePerTenGrams: Int = 0,
    var weight: Double = 0.0,
    var purity: Float = 100f,
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

    companion object {
        fun getPurityPercentages(): List<Float> {
            return arrayListOf<Float>().apply {
                (1..1000).forEach {
                    add(it.toFloat().div(10))
                }
            }
        }

        fun newTransaction(
            customerId: String,
            orderId: String,
            type: TransactionType,
            description: String = "",
            amount: Int = 0,
            metal: Metal = Metal.UNKNOWN,
            metalState: Int = 0,
            metalRatePerTenGrams: Int = 0,
            weight: Double = 0.0,
            purity: Float = 100f,
            stoneType: Int = 0,
            stoneCount: Int = 0
        ): Transaction {
            return Transaction(
                id = UUID.randomUUID().toString(),
                customerId = customerId,
                orderId = orderId,
                dateTime = OffsetDateTime.now(),
                type = type.value,
                description = description,
                amount = amount,
                metal = metal.value,
                metalState = metalState,
                metalRatePerTenGrams = metalRatePerTenGrams,
                weight = weight,
                purity = purity,
                stoneType = stoneType,
                stoneCount = stoneCount
            )
        }
    }
}

enum class TransactionType(val value: String) {
    MISCELLANEOUS_CHARGES("Miscellaneous Charges"), MAKING_CHARGE("Making Charge"), STONE_COST("Stone Cost"), DISCOUNT(
        "Discount"
    ),
    PAID_AMOUNT("Paid Amount"), METAL_PURCHASED("Metal Purchased"), METAL_EXTRA_ADDED("Metal Extra Added"), METAL_RECEIVED(
        "Metal Received"
    ),
    ORNAMENT_WEIGHT_FINAL(
        "Ornament Weight final"
    ),
    STONE_WEIGHT_MINUS("Stone Weight Minus"), WASTAGE("Wastage");

    companion object {

        fun getAllTransactionTypes(): List<String> {
            return arrayListOf<String>().apply {
                addAll(getAmountRelatedTransactionTypes())
                addAll(getMetalRelatedTransactionTypes())
                addAll(getStoneRelatedTransactionTypes())
            }
        }

        fun getAmountRelatedTransactionTypes(): List<String> {
            return arrayListOf(
                MAKING_CHARGE.value,
                MISCELLANEOUS_CHARGES.value,
                PAID_AMOUNT.value,
                DISCOUNT.value
            )
        }

        fun getStoneRelatedTransactionTypes(): List<String> {
            return arrayListOf(
                STONE_COST.value,
                STONE_WEIGHT_MINUS.value
            )
        }

        fun getMetalRelatedTransactionTypes(): List<String> {
            return arrayListOf(
                ORNAMENT_WEIGHT_FINAL.value,
                METAL_PURCHASED.value,
                METAL_RECEIVED.value,
                METAL_EXTRA_ADDED.value,
                WASTAGE.value
            )
        }
    }
}

enum class MetalState(val value: Int) {
    SOLID_METAL(1), OLD_ORNAMENT(2), PIECES(3), POWDER(4)
}