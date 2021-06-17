package com.viswakarma.jewelleryworks.viewmodel

import androidx.lifecycle.*
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Customer
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Metal
import com.viswakarma.jewelleryworks.model.repository.DataRepository
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Orders
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.util.*

class CreateOrderViewModel(private var dataRepository: DataRepository) : ViewModel() {

    private var customer: Customer? = null
    private var isSubmitted: MutableLiveData<Boolean> = MutableLiveData()

    fun createOrder(customerId: String, name: String, phone: String, description: String, amount: Int) {
        viewModelScope.launch {
            dataRepository.insertOrder(Orders(
                id = UUID.randomUUID().toString(),
                customerId = customerId,
                name = name,
                dateTime = OffsetDateTime.now(),
                phone = phone,
                description = description,
                metal = Metal.GOLD.ordinal
            ))
            isSubmitted.value = true
        }
    }

    fun getCustomersList(customerId: String): LiveData<List<Customer>> = liveData {
//        val items = listOf("Material", "Design", "Components", "Android")
        if(customerId.isBlank()) {
            emit(dataRepository.getAllCustomers())
        } else {
            val customer = dataRepository.getCustomerById(customerId)
            emit(arrayListOf(customer))
        }
//
    }

    fun getIsSumbitted(): LiveData<Boolean> {
        return isSubmitted
    }

    fun setSelectedCustomer(customer: Customer?) {
        this.customer = customer
    }
    fun getSelectedCustomer(): Customer? {
        return customer
    }

}
