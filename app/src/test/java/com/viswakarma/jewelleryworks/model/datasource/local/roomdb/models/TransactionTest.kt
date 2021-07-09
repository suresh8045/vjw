package com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models

import org.junit.After
import org.junit.Before

import org.junit.Test
import java.time.OffsetDateTime

class TransactionTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testEnumToString(){
        print(TransactionType.DISCOUNT.value)
    }

    @Test
    fun testFormats(){
        val txn = Transaction(
            id = "sds",
            customerId = "",
            weight = 10.5,
            purity = 99.9f,
            orderId = "",
            dateTime = OffsetDateTime.now(),
            type = TransactionType.METAL_RECEIVED.value,
            metal = Metal.GOLD.value,
            metalRatePerTenGrams = 50000,
            metalState = MetalState.SOLID_METAL.value,
        )
        assert(txn.getWeightFormat() == "W 10.500g")
        assert(txn.getPurityFormat() == "(99.9%)")
    }


}