package com.viswakarma.jewelleryworks.model.repository

import androidx.lifecycle.LiveData
import com.viswakarma.jewelleryworks.model.bussinessmodels.OrderDetails
import com.viswakarma.jewelleryworks.model.datasource.local.LocalDataSource
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Catalogue
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Customer
import com.viswakarma.jewelleryworks.model.datasource.remote.RemoteDataSource
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Order
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Transaction
import kotlinx.coroutines.flow.Flow


class DataRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {


    suspend fun getAllOrders() = localDataSource.getAllOrders()
    suspend fun insertOrder(order:Order) = localDataSource.insertOrder(order)

    fun getAllOrdersFlow() = localDataSource.getAllOrdersFlow()
    fun getAllOrdersFlow(searchText: String) = localDataSource.getAllOrdersFlow(searchText)
    suspend fun addNewCustomer(customer: Customer) {
        localDataSource.addNewCustomer(customer)
    }

    fun getAllTopCustomers(): Flow<List<Customer>> = localDataSource.getAllCustomersFlow()
    suspend fun getAllCustomers(): List<Customer> = localDataSource.getAllCustomers()
    suspend fun getCustomerById(customerId: String): Customer = localDataSource.getCustomerById(customerId)
    suspend fun addNewCatalogueItem(catalogue: Catalogue) = localDataSource.addNewCatalogueItem(catalogue)
    suspend fun getAllCatalogues(): List<Catalogue> = localDataSource.getAllCatalogues()
    fun getAllCatalogueItems(searchText: String): Flow<List<Catalogue>> = localDataSource.getAllCatalogueFlow(searchText)
    fun getCatalogueItemTypes(): List<String> = Catalogue.getItemTypes()
    suspend fun getOrderById(orderId: String): Order = localDataSource.getOrderById(orderId)
    fun getOrderDetails(orderId: String): Flow<OrderDetails> = localDataSource.getOrderDetails(orderId)
    suspend fun addNewTransaction(transaction: Transaction) = localDataSource.addNewTransaction(transaction)
    suspend fun getCatalogueItemByModelNo(modelNo: String) = localDataSource.getCatalogueItemByModelNo(modelNo)

}