package com.viswakarma.jewelleryworks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.viswakarma.jewelleryworks.model.repository.DataRepository
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Order

class DashboardViewModel(var dataRepository: DataRepository) : ViewModel() {
    fun getAllOrders(): LiveData<List<Order>> = dataRepository.getAllOrdersFlow().asLiveData()
}