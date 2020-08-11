package com.viswakarma.jewelleryworks.di

import androidx.appcompat.app.AppCompatActivity
import com.viswakarma.jewelleryworks.LoginActivity
import dagger.Binds
import dagger.Module


@Module
abstract class LoginActivityModule {

    @Binds
    @ActivityScoped
    abstract fun appCompatActivity(loginActivity: LoginActivity): AppCompatActivity



}