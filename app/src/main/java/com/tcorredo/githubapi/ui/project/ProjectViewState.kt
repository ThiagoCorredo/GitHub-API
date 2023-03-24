package com.tcorredo.githubapi.ui.project

import com.tcorredo.githubapi.data.domain.entity.Project
import com.tcorredo.githubapi.util.Constants.DEFAULT_VALUE

sealed class ProjectViewState {

    object Loading : ProjectViewState()

    data class Content(val projects: List<Project>?) : ProjectViewState()

    object EmptyState : ProjectViewState()

    object NoMorePage : ProjectViewState()

    data class ShowError(val code: Int = DEFAULT_VALUE, val message: String? = "") : ProjectViewState()
}
