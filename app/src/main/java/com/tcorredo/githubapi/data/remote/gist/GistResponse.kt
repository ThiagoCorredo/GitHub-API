package com.tcorredo.githubapi.data.remote.gist

import com.squareup.moshi.Json
import com.tcorredo.githubapi.data.remote.project.Owner
import java.util.*

data class GistResponse(
    @Json(name = "id") val id: String,
    @Json(name = "created_at") val createAt: Date,
    @Json(name = "updated_at") val updateAt: Date,
    @Json(name = "html_url") val htmlUrl: String,
    @Json(name = "files") val files: Map<String, GistFile>,
    @Json(name = "description") val description: String,
    @Json(name = "comments") val comments: Int,
    @Json(name = "owner") val owner: Owner
)

data class GistFile(
    @Json(name = "filename") val fileName: String,
)