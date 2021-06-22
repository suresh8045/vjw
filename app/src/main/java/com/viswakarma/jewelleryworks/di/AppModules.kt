package com.viswakarma.jewelleryworks.di

import com.viswakarma.jewelleryworks.AddCustomerViewModel
import com.viswakarma.jewelleryworks.AddToCatalogueViewModel
import com.viswakarma.jewelleryworks.OrdersViewModel
import com.viswakarma.jewelleryworks.TransactionsViewModel
import com.viswakarma.jewelleryworks.model.datasource.local.LocalDataSource
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.database.ViswakarmaDatabase
import com.viswakarma.jewelleryworks.model.datasource.preferences.Prefs
import com.viswakarma.jewelleryworks.model.datasource.remote.RemoteDataSource
import com.viswakarma.jewelleryworks.model.repository.DataRepository
import com.viswakarma.jewelleryworks.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { Prefs(get()) }
}

val databaseModule = module {
    single { ViswakarmaDatabase.getInstance(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DashboardViewModel(get()) }
    viewModel { CreateOrderViewModel(get()) }
    viewModel { NotificationsViewModel() }
    viewModel { CatalogueViewModel(get()) }
    viewModel { AddCustomerViewModel(get()) }
    viewModel { TransactionsViewModel(get()) }
    viewModel { OrdersViewModel(get()) }
    viewModel { AddToCatalogueViewModel(get()) }
}

val dataModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource() }
    single { DataRepository(get(), get()) }
}