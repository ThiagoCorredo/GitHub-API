package com.tcorredo.githubapi.data.remote.project

import com.squareup.moshi.Json

data class ProjectResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "full_name") val fullName: String,
    @Json(name = "owner") val owner: Owner,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String?,
    @Json(name = "forks") val forks: Int,
    @Json(name = "stargazers_count") val starCount: Int,
    @Json(name = "license") val license: License?
)

data class Owner(
    @Json(name = "login") val login: String,
    @Json(name = "avatar_url") val avatarUrl: String
)

data class License(
    @Json(name = "name") val name: String
)
