package com.tcorredo.githubapi.di

import com.tcorredo.githubapi.util.PagingManager
import org.koin.dsl.module

val appModule = module {
    single { PagingManager() }
}