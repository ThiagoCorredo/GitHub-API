package com.tcorredo.githubapi.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tcorredo.githubapi.BuildConfig
import com.tcorredo.githubapi.data.mapper.ProjectResponseToProjectDomainMapper
import com.tcorredo.githubapi.data.remote.GitHubService
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import okio.IOException
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "BASE_URL"
private const val APY_KEY = BuildConfig.GITHUB_API_KEY

val dataModule = module {
    single { GitHubService(get()) }

    single { ProjectResponseToProjectDomainMapper() }

    single { provideRetrofit(get(named(BASE_URL)), get(), get()) }

    single { provideMoshi() }

    single { provideOkHttpClient() }

    single(named(BASE_URL)) { "https://api.github.com/" }
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
        .addInterceptor(AuthHeaderInterceptor(APY_KEY))
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply { level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE }
        )
        .build()
}

class AuthHeaderInterceptor(private val apiKey: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val authenticatedRequest: Request = request.newBuilder()
            .header("Authorization", apiKey).build()
        return chain.proceed(authenticatedRequest)
    }
}
