package com.viswakarma.jewelleryworks.ui.dashboard

import androidx.lifecycle.*
import com.viswakarma.jewelleryworks.repository.ViswakarmaRepository
import com.viswakarma.jewelleryworks.roomdb.models.Orders
import javax.inject.Inject

class DashboardViewModel @Inject constructor(var viswakarmaRepository: ViswakarmaRepository) : ViewModel() {



    fun getAllOrders(): LiveData<List<Orders>> = viswakarmaRepository.getAllOrdersFlow().asLiveData()
}