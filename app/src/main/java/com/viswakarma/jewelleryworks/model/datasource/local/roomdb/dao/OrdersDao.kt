package com.viswakarma.jewelleryworks.model.datasource.local.roomdb.dao

import androidx.room.*
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Orders
import kotlinx.coroutines.flow.Flow

@Dao
interface OrdersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(orders: Orders)

    @Update
    suspend fun updateOrder(orders: Orders)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrders(orders: List<Orders>)

    @Query("DELETE from Orders")
    suspend fun deleteAllOrders()

    @Query("SELECT * FROM Orders")
    suspend fun getAllOrders():List<Orders>

    @Query("SELECT * FROM Orders WHERE orderId=(:id)")
    suspend fun getOrdersById(id:String):Orders

    @Query("SELECT * FROM Orders")
    fun getAllOrdersFlow(): Flow<List<Orders>>

}
