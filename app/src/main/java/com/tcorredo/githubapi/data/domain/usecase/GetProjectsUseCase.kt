package com.tcorredo.githubapi.data.domain.usecase

import com.tcorredo.githubapi.data.domain.repository.ProjectRepository

class GetProjectsUseCase(private val projectRepository: ProjectRepository) {
    suspend fun invoke() = projectRepository.getProjects()
}
