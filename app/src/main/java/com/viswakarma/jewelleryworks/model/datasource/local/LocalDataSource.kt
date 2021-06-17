package com.viswakarma.jewelleryworks.model.datasource.local

import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.database.ViswakarmaDatabase
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Customer
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Orders
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val viswakarmaDatabase: ViswakarmaDatabase
) {

    private val ordersDao get() = viswakarmaDatabase.ordersDao()
    private val customersDao get() = viswakarmaDatabase.customersDao()

    suspend fun getAllOrders() = ordersDao.getAllOrders()
    suspend fun insertOrder(order: Orders) = ordersDao.insertOrder(order)
    fun getAllOrdersFlow() = ordersDao.getAllOrdersFlow()
    suspend fun addNewCustomer(customer: Customer) = customersDao.insertCustomer(customer)
    fun getAllCustomersFlow(): Flow<List<Customer>> = customersDao.getAllCustomersFlow()
    suspend fun getAllCustomers(): List<Customer> = customersDao.getAllCustomers()
    suspend fun getCustomerById(customerId: String): Customer = customersDao.getCustomerById(customerId)
}