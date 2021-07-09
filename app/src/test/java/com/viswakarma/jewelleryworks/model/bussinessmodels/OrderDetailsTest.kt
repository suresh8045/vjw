package com.viswakarma.jewelleryworks.model.bussinessmodels

import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.OffsetDateTime

class OrderDetailsTest {

    private lateinit var orderDetails: OrderDetails
    private val orderId = "someOrderId"
    private val customerId = "someCustomerId"
    private lateinit var transactionArrayList1: List<Transaction>
    private lateinit var transactionArrayList2: List<Transaction>
    private lateinit var transactionArrayList3: List<Transaction>
    private lateinit var transactionArrayList4: List<Transaction>
    private lateinit var transactionArrayList5: List<Transaction>
    private lateinit var transactionArrayList6: List<Transaction>

    @Before
    fun setUp() {
        orderDetails = OrderDetails(
            order = Order(
            id = orderId,
            customerId = customerId,
            name = "CustomerName",
            phone = "1234567890",
            dateTime = OffsetDateTime.now(),
            image = "",
            modelNo = System.currentTimeMillis().div(1000).toString(),
            description = "",
            metal = Metal.GOLD.value,
            weight = 10.0
            ),
            catalogue = null
        )
        transactionArrayList1 = arrayListOf(
            Transaction(
                id = "",
                dateTime = OffsetDateTime.now(),
                type = TransactionType.MAKING_CHARGE.value,
                amount = 10000
            ),
            Transaction(
                id = "",
                dateTime = OffsetDateTime.now(),
                type = TransactionType.PAID_AMOUNT.value,
                amount = 3000
            ),
            Transaction(
                id = "",
                dateTime = OffsetDateTime.now(),
                type = TransactionType.DISCOUNT.value,
                amount = 1000
            )
        )
        transactionArrayList2 = arrayListOf(
            Transaction(
                id = "",
                dateTime = OffsetDateTime.now(),
                type = TransactionType.MISCELLANEOUS_CHARGES.value,
                amount = 250
            )
        ).apply { addAll(transactionArrayList1) }

        transactionArrayList3 = arrayListOf(
            Transaction(
                id = "",
                dateTime = OffsetDateTime.now(),
                type = TransactionType.PAID_AMOUNT.value,
                amount = 10000
            )
        ).apply{
            addAll(transactionArrayList2)
        }
        transactionArrayList4 = arrayListOf(
            Transaction(
                id = "",
                dateTime = OffsetDateTime.now(),
                type = TransactionType.METAL_PURCHASED.value,
                metalRatePerTenGrams = 50000,
                metal = Metal.GOLD.value,
                metalState = MetalState.SOLID_METAL.value,
                weight = 1.2,
                purity = 100f
            )
        ).apply{
            addAll(transactionArrayList3)
        }
        transactionArrayList5 = arrayListOf(
            Transaction(
                id = "",
                dateTime = OffsetDateTime.now(),
                type = TransactionType.METAL_PURCHASED.value,
                metalRatePerTenGrams = 50000,
                metal = Metal.GOLD.value,
                metalState = MetalState.SOLID_METAL.value,
                weight = 1.2,
                purity = 91.6f
            ) //amount will be 5496
        ).apply{
            addAll(transactionArrayList4)
        }
        transactionArrayList6 = arrayListOf(
            Transaction(
                id = "",
                dateTime = OffsetDateTime.now(),
                type = TransactionType.METAL_EXTRA_ADDED.value,
                metalRatePerTenGrams = 55000,
                metal = Metal.GOLD.value,
                metalState = MetalState.SOLID_METAL.value,
                weight = 2.3,
                purity = 100f
            ) //amount will be 5496
        ).apply{
            addAll(transactionArrayList5)
        }
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testTotalDueAmount(){
        orderDetails.apply {
            transactions = transactionArrayList1
        }
        var expectedTotalDueAmount = 6000
        var totalDueAmount = orderDetails.getTotalDueAmount()
        println("totalDueAmount: $totalDueAmount, expected: $expectedTotalDueAmount")
        assert(totalDueAmount == expectedTotalDueAmount)
        orderDetails.apply {
            transactions = transactionArrayList2
        }
        expectedTotalDueAmount = 6250
        totalDueAmount = orderDetails.getTotalDueAmount()
        println("total: $totalDueAmount, expected:$expectedTotalDueAmount")
        assert(totalDueAmount == expectedTotalDueAmount)
        orderDetails.apply {
            transactions = transactionArrayList3
        }
        expectedTotalDueAmount = -3750
        totalDueAmount = orderDetails.getTotalDueAmount()
        println("total: $totalDueAmount, expected:$expectedTotalDueAmount")
        assert(totalDueAmount == expectedTotalDueAmount)
        orderDetails.apply {
            transactions = transactionArrayList4
        }
        expectedTotalDueAmount = -3750+6000
        totalDueAmount = orderDetails.getTotalDueAmount()
        println("total: $totalDueAmount, expected:$expectedTotalDueAmount")
        assert(totalDueAmount == expectedTotalDueAmount)
        orderDetails.apply {
            transactions = transactionArrayList5
        }
        expectedTotalDueAmount = -3750+6000+5496
        totalDueAmount = orderDetails.getTotalDueAmount()
        println("total: $totalDueAmount, expected:$expectedTotalDueAmount")
        assert(totalDueAmount == expectedTotalDueAmount)
        orderDetails.apply {
            transactions = transactionArrayList6
        }
        expectedTotalDueAmount = -3750+6000+5496+12650
        totalDueAmount = orderDetails.getTotalDueAmount()
        println("total: $totalDueAmount, expected:$expectedTotalDueAmount")
        assert(totalDueAmount == expectedTotalDueAmount)

    }

    @Test
    fun testTotalWeightDue(){

    }
}