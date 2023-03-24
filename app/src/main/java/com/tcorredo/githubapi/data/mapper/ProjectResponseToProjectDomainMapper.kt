package com.tcorredo.githubapi.data.mapper

import com.tcorredo.githubapi.data.domain.Mapper
import com.tcorredo.githubapi.data.domain.entity.Project
import com.tcorredo.githubapi.data.remote.ProjectResponse

class ProjectResponseToProjectDomainMapper : Mapper<ProjectResponse, Project> {
    override fun invoke(projectResponse: ProjectResponse): Project {
        return Project(
            id = projectResponse.id,
            fullName = projectResponse.fullName,
            ownerName = projectResponse.owner.login,
            ownerAvatar = projectResponse.owner.avatarUrl,
            name = projectResponse.name,
            description = projectResponse.description,
            forks = projectResponse.forks,
            starCount = projectResponse.starCount,
            licenseName = projectResponse.license?.name
        )
    }
}
