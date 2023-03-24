package com.tcorredo.githubapi

import android.app.Application
import com.tcorredo.githubapi.di.appModule
import com.tcorredo.githubapi.di.dataModule
import com.tcorredo.githubapi.di.domainModule
import com.tcorredo.githubapi.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    protected open fun startKoin() {
        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    dataModule,
                    domainModule,
                    appModule,
                    viewModelModule
                )
            )
        }
    }
}
