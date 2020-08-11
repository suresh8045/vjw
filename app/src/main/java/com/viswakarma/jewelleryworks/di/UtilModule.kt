package com.viswakarma.jewelleryworks.di

import android.content.Context
import com.viswakarma.jewelleryworks.resourceprovider.ResourceProvider
import com.viswakarma.jewelleryworks.resourceprovider.ResourceProviderImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilModule {
    @Singleton
    @Provides
    fun provideResources(application: Context): ResourceProvider {
        return ResourceProviderImpl(application)
    }
}