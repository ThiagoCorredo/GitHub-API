package com.tcorredo.githubapi.data

import com.tcorredo.githubapi.data.remote.GitHubService
import com.tcorredo.githubapi.data.remote.ProjectResponse
import com.tcorredo.githubapi.data.domain.Mapper
import com.tcorredo.githubapi.data.domain.dispatchers.CoroutineDispatchers
import com.tcorredo.githubapi.data.domain.entity.Project
import com.tcorredo.githubapi.data.domain.repository.ProjectRepository
import com.tcorredo.githubapi.data.remote.ItemsResponse
import com.tcorredo.githubapi.util.PagingManager
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

class ProjectRepositoryImpl(
    private val userApiService: GitHubService,
    private val dispatchers: CoroutineDispatchers,
    private val responseToDomain: Mapper<ProjectResponse, Project>,
    private val pagingManager: PagingManager
) : ProjectRepository {

    private var projects: List<Project> = emptyList()

    private suspend fun getProjectsFromRemote(page: Int): Response<ItemsResponse> {
        return withContext(dispatchers.io) {
            userApiService.getRepositories(page)
        }
    }

    override suspend fun getProjects(): ResultState<List<Project>> {
        return withContext(dispatchers.io) {
            if (pagingManager.nextPage != 1 && !pagingManager.hasMore()) {
                ResultState.NoMorePage
            }

            try {
                val response = getProjectsFromRemote(pagingManager.nextPage)
                if (response.isSuccessful) {
                    pagingManager.savePageHeader(response.headers())

                    val data = response.body()?.response?.map(responseToDomain)
                    data.let {
                        if (!it.isNullOrEmpty()) {
                            projects = projects + it
                            ResultState.Success(projects)
                        } else {
                            ResultState.EmptyData
                        }
                    }
                } else {
                    ResultState.Error(HttpException(response))
                }
            } catch (exception: Exception) {
                ResultState.ErrorFatal(exception)
            }
        }
    }
}
