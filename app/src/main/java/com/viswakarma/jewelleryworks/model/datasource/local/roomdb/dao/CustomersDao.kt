package com.viswakarma.jewelleryworks.model.datasource.local.roomdb.dao

import androidx.room.*
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Customer
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: Customer)

    @Update
    suspend fun updateCustomer(customer: Customer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomers(customers: List<Customer>)

    @Query("DELETE from customers")
    suspend fun deleteAllCustomers()

    @Query("SELECT * FROM customers")
    suspend fun getAllCustomers():List<Customer>

    @Query("SELECT * FROM customers WHERE id=(:id)")
    suspend fun getCustomerById(id:String):Customer

    @Query("SELECT * FROM customers")
    fun getAllCustomersFlow(): Flow<List<Customer>>

}
