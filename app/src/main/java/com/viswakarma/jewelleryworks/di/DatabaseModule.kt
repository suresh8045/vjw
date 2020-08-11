package com.viswakarma.jewelleryworks.di


import android.app.Application
import androidx.room.Room
import com.viswakarma.jewelleryworks.roomdb.dao.OrdersDao
import com.viswakarma.jewelleryworks.roomdb.database.ViswakarmaDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {




    @Singleton
    @Provides
    fun providesRoomDatabase(mApplication: Application): ViswakarmaDatabase {
        return Room.databaseBuilder(mApplication, ViswakarmaDatabase::class.java, ViswakarmaDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }


    @Singleton
    @Provides
    fun providesOrdersDao(viswakarmaDatabase: ViswakarmaDatabase): OrdersDao {
        return viswakarmaDatabase.ordersDao()
    }


}
