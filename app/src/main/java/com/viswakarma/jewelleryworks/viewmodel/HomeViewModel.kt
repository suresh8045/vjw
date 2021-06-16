package com.viswakarma.jewelleryworks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Customer
import com.viswakarma.jewelleryworks.model.repository.DataRepository

class HomeViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private val _topCustomers: MutableLiveData<List<Customer>> = MutableLiveData()
    val topCustomers: LiveData<List<Customer>> get() = dataRepository.getAllTopCustomers().asLiveData()

    init {

    }

    fun getAllTopCustomers() = dataRepository.getAllTopCustomers().asLiveData()
}