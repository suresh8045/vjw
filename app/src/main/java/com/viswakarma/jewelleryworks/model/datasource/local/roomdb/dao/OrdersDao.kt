package com.viswakarma.jewelleryworks.model.datasource.local.roomdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.viswakarma.jewelleryworks.model.bussinessmodels.OrderDetails
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

    @Query("""SELECT * FROM orders
        WHERE name LIKE '%' || :searchText || '%' 
        or phone LIKE '%' || :searchText || '%' 
        or modelNo LIKE '%' || :searchText || '%' 
        or description LIKE '%' || :searchText || '%' 
        or metal LIKE '%' || :searchText || '%' 
        or weight LIKE '%' || :searchText || '%' 
        ORDER BY dateTime DESC""")
    fun getAllOrdersFlow(searchText: String): Flow<List<Order>>

    @Transaction
    @Query("SELECT * FROM orders WHERE id=:orderId")
    fun getOrderDetails(orderId: String): Flow<OrderDetails>

}
