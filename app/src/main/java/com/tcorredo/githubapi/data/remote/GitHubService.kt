package com.tcorredo.githubapi.data.remote

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("search/repositories?q=language:kotlin&sort=stars")
    suspend fun getRepositories(@Query("page") page: Int): Response<ItemsResponse>

    companion object {
        operator fun invoke(retrofit: Retrofit) = retrofit.create<GitHubService>()
    }
}
