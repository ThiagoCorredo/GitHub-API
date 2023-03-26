package com.tcorredo.githubapi.data.domain.repository

import com.tcorredo.githubapi.data.ResultState
import com.tcorredo.githubapi.data.domain.entity.Gist

interface GistRepository {
    suspend fun getGists(): ResultState<List<Gist>>
}