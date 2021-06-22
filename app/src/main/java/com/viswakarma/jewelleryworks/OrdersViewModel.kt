package com.viswakarma.jewelleryworks

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Order
import com.viswakarma.jewelleryworks.model.repository.DataRepository

class OrdersViewModel(private val dataRepository: DataRepository) : ViewModel() {

    val orders: LiveData<List<Order>> = dataRepository.getAllOrdersFlow().asLiveData()

}