package com.tcorredo.githubapi.data

import com.tcorredo.githubapi.data.domain.Mapper
import com.tcorredo.githubapi.data.domain.dispatchers.CoroutineDispatchers
import com.tcorredo.githubapi.data.domain.entity.Gist
import com.tcorredo.githubapi.data.domain.repository.GistRepository
import com.tcorredo.githubapi.data.remote.GitHubService
import com.tcorredo.githubapi.data.remote.gist.GistResponse
import com.tcorredo.githubapi.util.PagingManager
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

class GistRepositoryImpl(
    private val gitHubApiService: GitHubService,
    private val dispatchers: CoroutineDispatchers,
    private val responseToDomain: Mapper<GistResponse, Gist>,
    private val pagingManager: PagingManager
) : GistRepository {

    private var gists: List<Gist> = emptyList()

    private suspend fun getGistsFromRemote(page: Int): Response<List<GistResponse>> {
        return withContext(dispatchers.io) {
            gitHubApiService.getGists(page)
        }
    }

    override suspend fun getGists(): ResultState<List<Gist>> {
        return withContext(dispatchers.io) {
            if (pagingManager.nextPage != 1 && !pagingManager.hasMore()) {
                ResultState.NoMorePage
            }

            try {
                val response = getGistsFromRemote(pagingManager.nextPage)
                if (response.isSuccessful) {
                    pagingManager.savePageHeader(response.headers())

                    val data = response.body()?.map(responseToDomain)
                    data.let {
                        if (!it.isNullOrEmpty()) {
                            gists = gists + it
                            ResultState.Success(gists)
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