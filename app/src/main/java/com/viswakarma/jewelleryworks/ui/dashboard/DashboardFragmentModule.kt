package com.viswakarma.jewelleryworks.ui.dashboard

import androidx.fragment.app.Fragment
import com.viswakarma.jewelleryworks.di.FragmentScoped
import dagger.Binds
import dagger.Module

@Module
abstract class DashboardFragmentModule {
    @Binds
    @FragmentScoped
    abstract fun fragment(fragment: DashboardFragment): Fragment
}
