package com.tcorredo.githubapi.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tcorredo.githubapi.data.mapper.GistResponseToGistDomainMapper
import com.tcorredo.githubapi.data.mapper.ProjectResponseToProjectDomainMapper
import com.tcorredo.githubapi.data.remote.GitHubService
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "BASE_URL"

val testModule = module {
    single { GitHubService(get()) }

    single { ProjectResponseToProjectDomainMapper() }

    single { GistResponseToGistDomainMapper() }

    single { provideRetrofit(get(named(BASE_URL)), get(), get()) }

    single { provideMoshi() }

    single { provideOkHttpClient() }

    single(named(BASE_URL)) { "http://127.0.0.1:8080" }
}

private fun provideMoshi(): Moshi {
    return Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}

private fun provideRetrofit(baseUrl: String, moshi: Moshi, client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(baseUrl)
        .build()
}

private fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()
}
