package com.tcorredo.githubapi.di

import com.tcorredo.githubapi.ui.gist.GistViewModel
import com.tcorredo.githubapi.ui.project.ProjectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ProjectViewModel(get()) }
    viewModel { GistViewModel(get()) }
}