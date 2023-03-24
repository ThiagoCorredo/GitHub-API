package com.tcorredo.githubapi.data.domain.repository

import com.tcorredo.githubapi.data.ResultState
import com.tcorredo.githubapi.data.domain.entity.Project

interface ProjectRepository {
    suspend fun getProjects(): ResultState<List<Project>>
}
