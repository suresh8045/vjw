package com.viswakarma.jewelleryworks.model.repository

import com.viswakarma.jewelleryworks.model.datasource.local.LocalDataSource
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Customer
import com.viswakarma.jewelleryworks.model.datasource.remote.RemoteDataSource
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Orders
import kotlinx.coroutines.flow.Flow


class DataRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {


    suspend fun getAllOrders() = localDataSource.getAllOrders()
    suspend fun insertOrder(order:Orders) = localDataSource.insertOrder(order)

    fun getAllOrdersFlow() = localDataSource.getAllOrdersFlow()
    suspend fun addNewCustomer(customer: Customer) {
        localDataSource.addNewCustomer(customer)
    }

    fun getAllTopCustomers(): Flow<List<Customer>> = localDataSource.getAllCustomersFlow()
    suspend fun getAllCustomers(): List<Customer> = localDataSource.getAllCustomers()
    suspend fun getCustomerById(customerId: String): Customer = localDataSource.getCustomerById(customerId)

}