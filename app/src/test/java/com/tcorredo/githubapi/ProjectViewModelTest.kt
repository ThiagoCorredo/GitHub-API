package com.tcorredo.githubapi

import com.tcorredo.githubapi.data.ResultState
import com.tcorredo.githubapi.data.domain.entity.Project
import com.tcorredo.githubapi.data.domain.repository.ProjectRepository
import com.tcorredo.githubapi.data.domain.usecase.GetProjectsUseCase
import com.tcorredo.githubapi.ui.project.ProjectViewModel
import com.tcorredo.githubapi.ui.project.ProjectViewState
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

class ProjectViewModelTest : KoinTest {

    @get:Rule
    val instantExecutionRule = InstantExecutionRule()

    lateinit var viewModel: ProjectViewModel
    private val getProjectsUseCase: GetProjectsUseCase = mockk()
    private val projectRepository: ProjectRepository = mockk()

    @Before
    fun setUp() {
        startKoin {
            modules(module {
                single { viewModel }
                single { projectRepository }
                single { getProjectsUseCase }
            })
        }
        viewModel = ProjectViewModel(getProjectsUseCase)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun givenEmptyList_whenGettingApiResult_shouldReturnEmptyState() {
        coEvery { getProjectsUseCase.invoke() } returns ResultState.EmptyData

        viewModel.getProjects()

        assertEquals(viewModel.viewState.value, ProjectViewState.EmptyState)
    }

    @Test
    fun givenEmptyList_whenGettingApiResult_shouldReturnItemState() {
        val projects: List<Project> = listOf(mockk())

        coEvery { getProjectsUseCase.invoke() } returns ResultState.Success(projects)

        viewModel.getProjects()

        assertEquals(viewModel.viewState.value, ProjectViewState.Content(projects))
    }

    @Test
    fun givenEmptyList_whenGettingApiResult_shouldReturnNetworkErrorState() {
        val errorMessage = "Mock error"
        coEvery { getProjectsUseCase.invoke() } returns ResultState.ErrorFatal(
            Exception(
                errorMessage
            )
        )

        viewModel.getProjects()

        assertEquals(
            ProjectViewState.ShowError(message = errorMessage), viewModel.viewState.value
        )
    }
}