package com.viswakarma.jewelleryworks.model.bussinessmodels

import androidx.room.Embedded
import androidx.room.Relation
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Catalogue
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Order
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Transaction
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.TransactionType
import kotlin.math.roundToInt

data class OrderDetails(
    @Embedded
    val order: Order,
    @Relation(
        parentColumn = "modelNo",
        entityColumn = "modelNo"
    )
    val catalogue: Catalogue?,
    @Relation(
        parentColumn = "id",
        entityColumn = "orderId"
    )
    var transactions: List<Transaction> = arrayListOf()
){

    fun getTotalDueAmount():Int{
        val totalMakingCharge = transactions.filter { it.type == TransactionType.MAKING_CHARGE.value }.map {
            it.amount
        }.sum()
        val totalMiscellaneousCharges = transactions.filter { it.type == TransactionType.MISCELLANEOUS_CHARGES.value }.map {
            it.amount
        }.sum()
        val totalPaidAmount = transactions.filter { it.type == TransactionType.PAID_AMOUNT.value }.map {
            it.amount
        }.sum()
        val totalDiscount = transactions.filter { it.type == TransactionType.DISCOUNT.value }.map {
            it.amount
        }.sum()
        val totalAmountForMetalPurchased = transactions.filter { it.type == TransactionType.METAL_PURCHASED.value }.map {
            val amount = it.metalRatePerTenGrams.div(10).times(it.weight.times(it.purity.div(100))).roundToInt()
            amount
        }.sum()
        val totalAmountForMetalExtraAdded = transactions.filter { it.type == TransactionType.METAL_EXTRA_ADDED.value }.map {
            val amount = it.metalRatePerTenGrams.div(10).times(it.weight.times(it.purity.div(100))).roundToInt()
            amount
        }.sum()
        return totalMakingCharge.plus(totalMiscellaneousCharges).plus(totalAmountForMetalPurchased).plus(totalAmountForMetalExtraAdded).minus(totalDiscount).minus(totalPaidAmount)
    }

    fun getTotalDueWeight():Double{
        val totalOrnamentWeight = transactions.filter{ it.type == TransactionType.ORNAMENT_WEIGHT_FINAL.value }.map {
            val weight = it.weight
        }
        return 0.0
    }

    fun getTotalCharges(): Int {
        val totalMakingCharge = transactions.filter { it.type == TransactionType.MAKING_CHARGE.value }.map {
            it.amount
        }.sum()
        val totalMiscellaneousCharges = transactions.filter { it.type == TransactionType.MISCELLANEOUS_CHARGES.value }.map {
            it.amount
        }.sum()
        val totalDiscount = transactions.filter { it.type == TransactionType.DISCOUNT.value }.map {
            it.amount
        }.sum()
        return totalMakingCharge.plus(totalMiscellaneousCharges).minus(totalDiscount)
    }

    fun getTotalPaidAmount(): Int {
        return transactions.filter { it.type == TransactionType.PAID_AMOUNT.value }.map {
            it.amount
        }.sum()
    }


}