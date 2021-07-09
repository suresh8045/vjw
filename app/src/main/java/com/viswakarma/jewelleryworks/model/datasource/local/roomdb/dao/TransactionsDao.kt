package com.viswakarma.jewelleryworks.model.datasource.local.roomdb.dao

import androidx.room.*
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(transactions: List<Transaction>)

    @Query("DELETE from transactions")
    suspend fun deleteAllTransactions()

    @Query("SELECT * FROM transactions")
    suspend fun getAllTransactions():List<Transaction>

    @Query("SELECT * FROM transactions WHERE id=(:id)")
    suspend fun getTransactionsById(id:String):Transaction

    @Query("SELECT * FROM transactions")
    fun getAllTransactionsFlow(): Flow<List<Transaction>>

}
