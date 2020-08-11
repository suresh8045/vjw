package com.viswakarma.jewelleryworks.ui.createorder

import androidx.fragment.app.Fragment
import com.viswakarma.jewelleryworks.di.FragmentScoped
import dagger.Binds
import dagger.Module

@Module
abstract class CreateOrderFragmentModule {
    @Binds
    @FragmentScoped
    abstract fun fragment(fragment: CreateOrderFragment): Fragment
}
