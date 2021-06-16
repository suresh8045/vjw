package com.viswakarma.jewelleryworks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viswakarma.jewelleryworks.model.repository.DataRepository
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Orders
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

class CreateOrderViewModel(private var dataRepository: DataRepository) : ViewModel() {

    private var isSubmitted: MutableLiveData<Boolean> = MutableLiveData()

    fun createOrder(customerId: String, name: String, phone: String, description: String, amount: Int) {
        viewModelScope.launch {
            dataRepository.insertOrder(Orders(
                customerId = customerId,
                name = name,
                dateTime = OffsetDateTime.now(),
                phone = phone,
                description = description,
                totalAmount = amount
            ))
            isSubmitted.value = true
        }
    }


    fun getIsSumbitted(): LiveData<Boolean> {
        return isSubmitted
    }

}
