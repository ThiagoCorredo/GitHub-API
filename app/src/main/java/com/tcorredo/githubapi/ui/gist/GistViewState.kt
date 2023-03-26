package com.tcorredo.githubapi.ui.gist

import com.tcorredo.githubapi.data.domain.entity.Gist
import com.tcorredo.githubapi.util.Constants.DEFAULT_VALUE

sealed class GistViewState {

    object Loading : GistViewState()

    data class Content(val projects: List<Gist>?) : GistViewState()

    object EmptyState : GistViewState()

    object NoMorePage : GistViewState()

    data class ShowError(val code: Int = DEFAULT_VALUE, val message: String? = "") : GistViewState()
}
