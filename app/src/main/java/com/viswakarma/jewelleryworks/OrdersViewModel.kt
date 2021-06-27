package com.viswakarma.jewelleryworks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Order
import com.viswakarma.jewelleryworks.model.repository.DataRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class OrdersViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private var ordersListUpdateJob: Job? = null
    private var searchQuery: String = ""
    private val _orders: MutableLiveData<List<Order>> = MutableLiveData()
    val orders: LiveData<List<Order>> get() = _orders

    init {
        updateRecyclerViewWithOrdersList()
    }

    private fun updateRecyclerViewWithOrdersList(searchQuery: String = this.searchQuery){
        ordersListUpdateJob?.cancel()
        ordersListUpdateJob = viewModelScope.launch {
            dataRepository.getAllOrdersFlow(searchQuery).map { ordersList ->
                _orders.postValue(ordersList)
            }.collect()
        }
    }

    fun setSearchQuery(searchQuery: String){
        this.searchQuery = searchQuery
        updateRecyclerViewWithOrdersList()
    }

}