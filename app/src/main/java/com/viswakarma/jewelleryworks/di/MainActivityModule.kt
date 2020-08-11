package com.viswakarma.jewelleryworks.di

import androidx.appcompat.app.AppCompatActivity
import com.viswakarma.jewelleryworks.MainActivity
import com.viswakarma.jewelleryworks.ui.createorder.CreateOrderFragment
import com.viswakarma.jewelleryworks.ui.createorder.CreateOrderFragmentModule
import com.viswakarma.jewelleryworks.ui.dashboard.DashboardFragment
import com.viswakarma.jewelleryworks.ui.dashboard.DashboardFragmentModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainActivityModule {

    @Binds
    @ActivityScoped
    abstract fun appCompatActivity(mainActivity: MainActivity): AppCompatActivity

    @FragmentScoped
    @ContributesAndroidInjector(modules = [DashboardFragmentModule::class])
    abstract fun dashboardFragmentInjector(): DashboardFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [CreateOrderFragmentModule::class])
    abstract fun createOrderFragmentInjector(): CreateOrderFragment

}