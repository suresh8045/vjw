package com.viswakarma.jewelleryworks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Metal
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Transaction
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.TransactionType
import com.viswakarma.jewelleryworks.model.repository.DataRepository
import java.time.OffsetDateTime

class OrderDetailsViewModel(private val dataRepository: DataRepository) : ViewModel() {


    private val _transactions : MutableLiveData<List<Transaction>> = MutableLiveData()
    val transactions: LiveData<List<Transaction>> get() = _transactions


    init {
        _transactions.value = arrayListOf<Transaction>().apply {
            add(
                Transaction(
                    id = "sd",
                    customerId = "sds",
                    orderId = "sd",
                    dateTime = OffsetDateTime.now(),
                    type = TransactionType.METAL_RECEIVED.type,
                    description = "some description here",
                    weight = 10.500,
                    purity = 80f,
                    metal = Metal.GOLD.value,
                    metalRatePerTenGrams = 50000
                )
            )
            add(
                Transaction(
                    id = "sd",
                    customerId = "sds",
                    orderId = "sd",
                    dateTime = OffsetDateTime.now(),
                    type = TransactionType.METAL_RECEIVED.type,
                    description = "some description here and long text to display",
                    weight = 10.500,
                    purity = 80f,
                    metal = Metal.GOLD.value,
                    metalRatePerTenGrams = 50000
                )
            )
        }
    }

}
