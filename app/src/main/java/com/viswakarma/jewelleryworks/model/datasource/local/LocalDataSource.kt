package com.viswakarma.jewelleryworks.model.datasource.local

import com.viswakarma.jewelleryworks.model.bussinessmodels.OrderDetails
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.database.ViswakarmaDatabase
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Catalogue
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Customer
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Order
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Transaction
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val viswakarmaDatabase: ViswakarmaDatabase
) {

    private val ordersDao get() = viswakarmaDatabase.ordersDao()
    private val customersDao get() = viswakarmaDatabase.customersDao()
    private val catalogueDao get() = viswakarmaDatabase.catalogueDao()
    private val transactionDao get() = viswakarmaDatabase.transactionDao()

    suspend fun getAllOrders() = ordersDao.getAllOrders()
    suspend fun insertOrder(order: Order) = ordersDao.insertOrder(order)
    fun getAllOrdersFlow() = ordersDao.getAllOrdersFlow()
    fun getAllOrdersFlow(searchText: String) = ordersDao.getAllOrdersFlow(searchText)
    suspend fun addNewCustomer(customer: Customer) = customersDao.insertCustomer(customer)
    fun getAllCustomersFlow(): Flow<List<Customer>> = customersDao.getAllCustomersFlow()
    suspend fun getAllCustomers(): List<Customer> = customersDao.getAllCustomers()
    suspend fun getCustomerById(customerId: String): Customer = customersDao.getCustomerById(customerId)
    suspend fun addNewCatalogueItem(catalogue: Catalogue) = catalogueDao.insertCustomer(catalogue)
    suspend fun getAllCatalogues(): List<Catalogue> = catalogueDao.getAllCatalogues()
    fun getAllCatalogueFlow(searchText: String): Flow<List<Catalogue>> = catalogueDao.getAllCataloguesFlow(searchText)
    suspend fun getOrderById(orderId: String): Order = ordersDao.getOrdersById(orderId)
    fun getOrderDetails(orderId: String): Flow<OrderDetails> = ordersDao.getOrderDetails(orderId)
    suspend fun addNewTransaction(transaction: Transaction) = transactionDao.insertTransaction(transaction)
    suspend fun getCatalogueItemByModelNo(modelNo: String) = catalogueDao.getCatalogueByModelNo(modelNo)

}