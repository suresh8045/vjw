package com.viswakarma.jewelleryworks.repository

import com.viswakarma.jewelleryworks.datasources.ViswakarmaLocalDataSource
import com.viswakarma.jewelleryworks.datasources.VRemoteDataSource
import com.viswakarma.jewelleryworks.roomdb.models.Orders
import javax.inject.Inject


class ViswakarmaRepository @Inject constructor(
    private val remoteDataSource: VRemoteDataSource,
    private val localDataSource: ViswakarmaLocalDataSource
) {


    suspend fun getAllOrders() = localDataSource.getAllOrders()
    suspend fun insertOrder(order:Orders) = localDataSource.insertOrder(order)

    fun getAllOrdersFlow() = localDataSource.getAllOrdersFlow()

}