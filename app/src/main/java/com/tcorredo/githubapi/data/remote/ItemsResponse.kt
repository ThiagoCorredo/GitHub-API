package com.tcorredo.githubapi.data.remote

import com.squareup.moshi.Json

data class ItemsResponse(@Json(name = "items") val response: List<ProjectResponse>)