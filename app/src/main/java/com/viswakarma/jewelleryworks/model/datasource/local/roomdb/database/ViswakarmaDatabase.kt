package com.viswakarma.jewelleryworks.model.datasource.local.roomdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.dao.CatalogueDao
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.dao.CustomersDao
import com.viswakarma.jewelleryworks.model.util.OffsetDateTimeConverter
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.database.ViswakarmaDatabase.Companion.VERSION
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.dao.OrdersDao
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.dao.TransactionDao
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Catalogue
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Customer
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Order
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Transaction


@Database(
    entities = [
        Customer::class,
        Order::class,
        Transaction::class,
        Catalogue::class
    ], version = VERSION, exportSchema = false
)
@TypeConverters(OffsetDateTimeConverter::class)
abstract class ViswakarmaDatabase : RoomDatabase() {

    abstract fun ordersDao(): OrdersDao
    abstract fun customersDao(): CustomersDao
    abstract fun catalogueDao(): CatalogueDao
    abstract fun transactionDao(): TransactionDao

    companion object {
        private const val DATABASE_NAME = "viswakarma"
        internal const val VERSION = 1

        fun getInstance(context: Context): ViswakarmaDatabase {
            return Room.databaseBuilder(context, ViswakarmaDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}