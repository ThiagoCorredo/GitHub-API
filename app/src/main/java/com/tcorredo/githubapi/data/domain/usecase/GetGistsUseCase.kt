package com.tcorredo.githubapi.data.domain.usecase

import com.tcorredo.githubapi.data.domain.repository.GistRepository

class GetGistsUseCase(private val gistRepository: GistRepository) {
    suspend fun invoke() = gistRepository.getGists()
}
