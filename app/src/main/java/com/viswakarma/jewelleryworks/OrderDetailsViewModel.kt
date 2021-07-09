package com.viswakarma.jewelleryworks

import androidx.lifecycle.*
import com.viswakarma.jewelleryworks.model.bussinessmodels.OrderDetails
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Metal
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Order
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Transaction
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.TransactionType
import com.viswakarma.jewelleryworks.model.repository.DataRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

class OrderDetailsViewModel(private val dataRepository: DataRepository) : ViewModel() {


    fun orderDetails(orderId: String): LiveData<OrderDetails> {
        return dataRepository.getOrderDetails(orderId).asLiveData()
    }



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
                    type = TransactionType.METAL_RECEIVED.value,
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
                    type = TransactionType.METAL_RECEIVED.value,
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
