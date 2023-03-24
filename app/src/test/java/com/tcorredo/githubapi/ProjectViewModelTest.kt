package com.tcorredo.githubapi

import com.tcorredo.githubapi.data.ResultState
import com.tcorredo.githubapi.data.domain.entity.Project
import com.tcorredo.githubapi.data.domain.usecase.GetProjectsUseCase
import com.tcorredo.githubapi.data.remote.ItemsResponse
import com.tcorredo.githubapi.ui.project.ProjectViewModel
import com.tcorredo.githubapi.ui.project.ProjectViewState
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class ProjectViewModelTest {

    @get:Rule
    val instantExecutionRule = InstantExecutionRule()

    lateinit var viewModel: ProjectViewModel
    private val getProjectsUseCase: GetProjectsUseCase = mockk()

    @Before
    fun setup() {
        viewModel = ProjectViewModel(getProjectsUseCase)
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
        val response: Response<ItemsResponse> = mockk()

        coEvery { getProjectsUseCase.invoke() } throws HttpException(response)

        viewModel.getProjects()

        assertEquals(viewModel.viewState.value, ProjectViewState.ShowError(message = "Timeout"))
    }
}