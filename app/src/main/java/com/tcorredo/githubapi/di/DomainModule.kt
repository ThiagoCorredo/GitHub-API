package com.tcorredo.githubapi.di

import com.tcorredo.githubapi.data.GistRepositoryImpl
import com.tcorredo.githubapi.data.ProjectRepositoryImpl
import com.tcorredo.githubapi.data.domain.dispatchers.CoroutineDispatchers
import com.tcorredo.githubapi.data.domain.dispatchers.CoroutineDispatchersImpl
import com.tcorredo.githubapi.data.domain.repository.GistRepository
import com.tcorredo.githubapi.data.domain.repository.ProjectRepository
import com.tcorredo.githubapi.data.domain.usecase.GetGistsUseCase
import com.tcorredo.githubapi.data.domain.usecase.GetProjectsUseCase
import com.tcorredo.githubapi.data.mapper.GistResponseToGistDomainMapper
import com.tcorredo.githubapi.data.mapper.ProjectResponseToProjectDomainMapper
import org.koin.dsl.module

val domainModule = module {
    single<CoroutineDispatchers> { CoroutineDispatchersImpl() }

    single<ProjectRepository> {
        ProjectRepositoryImpl(
            get(),
            get(),
            responseToDomain = get<ProjectResponseToProjectDomainMapper>(),
            get()
        )
    }

    single<GistRepository> {
        GistRepositoryImpl(
            get(),
            get(),
            responseToDomain = get<GistResponseToGistDomainMapper>(),
            get()
        )
    }

    factory { GetProjectsUseCase(get()) }

    factory { GetGistsUseCase(get()) }
}
