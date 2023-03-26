package com.tcorredo.githubapi.ui.gist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcorredo.githubapi.data.ResultState
import com.tcorredo.githubapi.data.domain.usecase.GetGistsUseCase
import kotlinx.coroutines.launch

class GistViewModel(
    private val getGistsUseCase: GetGistsUseCase
) : ViewModel() {
    private val _viewState = MutableLiveData<GistViewState>()
    val viewState: LiveData<GistViewState> = _viewState

    fun getGists() {
        viewModelScope.launch {
            _viewState.postValue(GistViewState.Loading)

            when (val result = getGistsUseCase.invoke()) {
                is ResultState.Success -> _viewState.value = GistViewState.Content(result.data)
                is ResultState.EmptyData -> _viewState.value = GistViewState.EmptyState
                is ResultState.NoMorePage -> _viewState.value = GistViewState.NoMorePage
                is ResultState.Error -> _viewState.value =
                    GistViewState.ShowError(code = result.error.code())
                is ResultState.ErrorFatal -> _viewState.value =
                    GistViewState.ShowError(message = result.exception.message)
            }
        }
    }
}