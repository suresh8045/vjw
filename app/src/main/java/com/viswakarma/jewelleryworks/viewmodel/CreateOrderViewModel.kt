package com.viswakarma.jewelleryworks.viewmodel

import androidx.lifecycle.*
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Customer
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Metal
import com.viswakarma.jewelleryworks.model.repository.DataRepository
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Order
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.util.*

class CreateOrderViewModel(private var dataRepository: DataRepository) : ViewModel() {

    private var customer: Customer? = null
    private var isSubmitted: MutableLiveData<Boolean> = MutableLiveData()

    fun createOrder(customerId: String, name: String, phone: String, metal: Metal, model: String, description: String, weight: Double) {
        viewModelScope.launch {
            dataRepository.insertOrder(Order(
                id = UUID.randomUUID().toString(),
                dateTime = OffsetDateTime.now(),
                customerId = customerId,
                name = name,
                phone = phone,
                modelNo = model,
                description = description,
                metal = metal.value,
                weight = weight
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
