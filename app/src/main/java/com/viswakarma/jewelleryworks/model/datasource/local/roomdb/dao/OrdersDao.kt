package com.viswakarma.jewelleryworks.model.datasource.local.roomdb.dao

import androidx.room.*
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface OrdersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)

    @Update
    suspend fun updateOrder(order: Order)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrders(orders: List<Order>)

    @Query("DELETE from Orders")
    suspend fun deleteAllOrders()

    @Query("SELECT * FROM Orders")
    suspend fun getAllOrders():List<Order>

    @Query("SELECT * FROM Orders WHERE id=(:id)")
    suspend fun getOrdersById(id:String):Order

    @Query("SELECT * FROM Orders")
    fun getAllOrdersFlow(): Flow<List<Order>>

}
