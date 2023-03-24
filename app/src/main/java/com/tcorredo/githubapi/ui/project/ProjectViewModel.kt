package com.tcorredo.githubapi.ui.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcorredo.githubapi.data.ResultState
import com.tcorredo.githubapi.data.domain.usecase.GetProjectsUseCase
import kotlinx.coroutines.launch

class ProjectViewModel(
    private val getProjectsUseCase: GetProjectsUseCase
) : ViewModel() {
    private val _viewState = MutableLiveData<ProjectViewState>()
    val viewState: LiveData<ProjectViewState> = _viewState

    fun getProjects() {
        viewModelScope.launch {
            _viewState.postValue(ProjectViewState.Loading)

            when (val result = getProjectsUseCase.invoke()) {
                is ResultState.Success -> _viewState.value = ProjectViewState.Content(result.data)
                is ResultState.EmptyData -> _viewState.value = ProjectViewState.EmptyState
                is ResultState.NoMorePage -> _viewState.value = ProjectViewState.NoMorePage
                is ResultState.Error -> _viewState.value =
                    ProjectViewState.ShowError(code = result.error.code())
                is ResultState.ErrorFatal -> _viewState.value =
                    ProjectViewState.ShowError(message = result.exception.message)
            }
        }
    }
}