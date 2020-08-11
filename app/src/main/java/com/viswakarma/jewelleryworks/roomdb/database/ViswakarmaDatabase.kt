package com.viswakarma.jewelleryworks.roomdb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.viswakarma.jewelleryworks.roomdb.database.ViswakarmaDatabase.Companion.VERSION
import com.viswakarma.jewelleryworks.roomdb.dao.OrdersDao
import com.viswakarma.jewelleryworks.roomdb.models.Orders


@Database(entities = [
    Orders::class
], version = VERSION, exportSchema = false)
abstract class ViswakarmaDatabase :RoomDatabase() {

    abstract fun ordersDao(): OrdersDao


    companion object {
        const val DATABASE_NAME = "viswakarma"
        internal const val VERSION = 1
    }
}