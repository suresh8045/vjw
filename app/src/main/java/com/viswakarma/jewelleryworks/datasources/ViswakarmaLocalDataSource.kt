package com.viswakarma.jewelleryworks.datasources

import com.viswakarma.jewelleryworks.roomdb.database.ViswakarmaDatabase
import com.viswakarma.jewelleryworks.roomdb.dao.OrdersDao
import com.viswakarma.jewelleryworks.roomdb.models.Orders
import javax.inject.Inject

class ViswakarmaLocalDataSource @Inject constructor(
    private val viswakarmaDatabase: ViswakarmaDatabase,
    private val ordersDao: OrdersDao
){


    suspend fun getAllOrders() = ordersDao.getAllOrders()
    suspend fun insertOrder(order: Orders) = ordersDao.insertOrder(order)
    fun getAllOrdersFlow() = ordersDao.getAllOrdersFlow()


}