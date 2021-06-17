package com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models

import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class TransactionsTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testEnumToString(){
        print(TransactionType.DISCOUNT.type)
    }
}