/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.viswakarma.jewelleryworks.di


import android.app.Application
import android.content.Context
import com.viswakarma.jewelleryworks.MainActivity
import com.viswakarma.jewelleryworks.ViswakarmaApplication
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Module(includes = [ AndroidInjectionModule::class,
    ViewModelModule::class,
    RetrofitModule::class,
    DatabaseModule::class,
    UtilModule::class,
    ServicesModule::class,
    AndroidSupportInjectionModule::class])
abstract class AppModule {



    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivityInjector(): MainActivity


    @Binds
    @Singleton
    abstract fun application(app: ViswakarmaApplication): Application

    @Binds
    abstract fun context(application: ViswakarmaApplication): Context

}
